package Service;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.io.File;
import java.text.DecimalFormat;

public class FileInfo {
    private String fileName;
    private String size;
    private String isFile;


    public FileInfo(String fileName, String size, String isFile) {
        this.fileName = fileName;
        this.size = size;
        this.isFile = isFile;
    }

    public FileInfo() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getisFile() {
        return isFile;
    }

    public void setisFile(String isFile) {
        this.isFile = isFile;
    }

    public static String getFileSize(File file){
        FileChannel fc = null;
        String size = "";
        try {
            if(file.exists() && file.isFile()){
                String fileName = file.getName();
                FileInputStream fis = new FileInputStream(file);
                fc = fis.getChannel();
                size = unitTrans(fc.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null!=fc){
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

    /**单位转换
     *
     * @param sizeOri
     * @return
     */
    private static String unitTrans(long sizeOri){
        DecimalFormat df = new DecimalFormat("#.00");
        String size;
        if (sizeOri < 1024) {
            size = df.format((double) sizeOri) + "BT";
        } else if (sizeOri < 1048576) {
            size = df.format((double) sizeOri / 1024) + "KB";
        } else if (sizeOri < 1073741824) {
            size = df.format((double) sizeOri / 1048576) + "MB";
        } else {
            size = df.format((double) sizeOri / 1073741824) +"GB";
        }
        return size;
    }
}
