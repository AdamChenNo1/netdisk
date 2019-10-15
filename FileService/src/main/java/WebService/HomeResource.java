package WebService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import Service.FileInfo;
import Service.UserSpace;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import pojo.Msg;

@Path("home")
public class HomeResource {

	@QueryParam("user")
	@DefaultValue("blank")
    private String homePath;

    @Path("/{child}")
	public ChildResource childResource(@PathParam("child") String path){
		return new ChildResource(homePath +"/" + path);
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Msg uploadFile(@Context HttpServletRequest req,
									@Context ServletContext ctx) throws Exception {
		// 上传配置
		int maxFileSize = 50*1024*1024; //50M
		int maxMemSize = 50*1024*1024; //50M
		int maxRequesSize = 50*1024*1024; //50M
		if (!ServletFileUpload.isMultipartContent(req)) {
			// 如果不是则停止
			return Msg.failed().add("415","Error: 表单必须包含enctype=multipart/form-data");
		}
        String USER_HOME_RELATIVE_PATH = homePath + File.separator;
		String USER_HOME_PATH = ctx.getInitParameter("netdisk")+ USER_HOME_RELATIVE_PATH;
		System.out.println(USER_HOME_RELATIVE_PATH);
		//验证上传的类型
		String contentType = req.getContentType();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//设置内存中存储文件的最大值
		factory.setSizeThreshold(maxMemSize);
		//本地存储的数据大于maxMemSize将产生临时文件并存储于临时目录中
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		//创建一个新的文件上传处理程序
		ServletFileUpload upload = new ServletFileUpload(factory);
		//设置最大上传文件的大小
		upload.setSizeMax(maxFileSize);
		//设置最大请求值 (包含文件和表单数据)
		upload.setSizeMax(maxRequesSize);
		//中文处理
		upload.setHeaderEncoding("UTF-8");
		try {
			//解析获取的文件
			List<FileItem> fileItems = upload.parseRequest(req);
			//处理上传的文件
			Iterator<FileItem> i = fileItems.iterator();
			while (i.hasNext()) {
				FileItem fi =(FileItem)i.next();
				if (!fi.isFormField()) {
					//获取上传文件的参数
					String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    if(fi.getName().startsWith(".")){
						// 文件名称生成策略（UUID uuid = UUID.randomUUID()）
						Date d = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
						String formatDate = format.format(d);
						String str = "";
						for(int j = 0 ;j <5; j++){
							int n = (int)(Math.random()*90)+10;
							str += n;
						}
                    	fileName = formatDate + str + fileName;
					}
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();
					//写入文件
					String filePath = USER_HOME_PATH + fileName;
					File file =new File(filePath);
					fi.write(file);
				}
			}
		}catch(Exception e) {
		    e.printStackTrace();
		}

		Map<String,Object> map = new HashMap<>();
		List<FileInfo> fileInfoList = UserSpace.getFileList(USER_HOME_PATH);
		map.put("fileList",fileInfoList);
		map.put("filesInfo",fileInfoList.size());
		return Msg.sucess().add("info",map);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Msg getFileList(@Context ServletContext ctx){
		String USER_HOME_RELATIVE_PATH = homePath + File.separator;
		String USER_HOME_PATH = ctx.getInitParameter("netdisk")+ USER_HOME_RELATIVE_PATH;
		Map<String,Object> map = new HashMap<>();
		List<FileInfo> fileInfoList = UserSpace.getFileList(USER_HOME_PATH);
	    map.put("fileList",fileInfoList);
	    map.put("filesInfo",fileInfoList.size());
		return Msg.sucess().add("info",map);
    }

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Msg deleteFile(@Context ServletContext ctx){
		String USER_HOME_PATH = ctx.getInitParameter("netdisk")+ homePath;
		String parentPath = USER_HOME_PATH.substring(0,USER_HOME_PATH.lastIndexOf('/'));
		System.out.println(parentPath);
		UserSpace.deleteFile(USER_HOME_PATH);

		Map<String,Object> map = new HashMap<>();
		List<FileInfo> fileInfoList = UserSpace.getFileList(parentPath);
		map.put("fileList",fileInfoList);
		map.put("filesInfo",fileInfoList.size());
		return Msg.sucess().add("info",map);
	}
}
