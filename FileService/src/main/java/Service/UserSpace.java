package Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserSpace {
    public static List<FileInfo> getFileList(String user_path) {
        Path path = Paths.get(user_path);
        boolean pathExists = Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        if(!pathExists){
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<FileInfo> fileInfoList = new ArrayList<>();
        File[] fileList = new File(user_path).listFiles();
        if (fileList != null) {
            for(File f:fileList){
                boolean isFile = f.isFile();
                if(isFile){
                    fileInfoList.add(new FileInfo(f.getName(),FileInfo.getFileSize(f),"file"));
                }else {
                    fileInfoList.add(new FileInfo(f.getName(),"-","dir"));
                }
            }
        }
        return fileInfoList;
    }

    public static void deleteFile(String filePath){
        Path path = Paths.get(filePath);
        boolean pathExists = Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        if(pathExists){
            try {
                Files.delete(path);
            } catch (IOException e) {
                // 删除文件失败
                e.printStackTrace();
            }
        }
    }

    public static void downloadAsStream(String filePath){

    }
}
