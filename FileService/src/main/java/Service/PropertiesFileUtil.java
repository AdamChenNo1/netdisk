package Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesFileUtil {
    public static String getProperties(String filePath,String keyWord){
        Properties prop = new Properties();
        String value = null;
        try {
            InputStream inputStream = PropertiesFileUtil.class.getClassLoader().getResourceAsStream(filePath);
            prop.load(inputStream);
            value = prop.getProperty(keyWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Map getProperties(String filePath){
        Properties prop = new Properties();
        Map<String,String> map = new HashMap<>();
        InputStream inputStream = PropertiesFileUtil.class.getClassLoader().getResourceAsStream(filePath);
        try {
            //InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            prop.load(inputStream);
            Set<String> keys = prop.stringPropertyNames();
            for(String key:keys){
                map.put(key,prop.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
