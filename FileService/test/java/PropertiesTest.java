import Service.PropertiesFileUtil;
import org.junit.Test;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class PropertiesTest {
    @Test
    public void test(){
        String redis = PropertiesFileUtil.getProperties("Redis.properties","redis.host");
        System.out.println(redis);
    }
    @Test
    public void test2(){
        URL url = PropertiesFileUtil.class.getClassLoader().getResource("Redis.properties");
        System.out.println(url);
    }
    @Test
    public void test3(){
        Map map = PropertiesFileUtil.getProperties("Redis.properties");
        Iterator it = map.keySet().iterator();
        while(it.hasNext()){
            Object key =  it.next();
            System.out.println((String) key+":"+map.get(key) + " " +  (map.get(key) instanceof String?"String":"Not String") +" "+ (map.get(key) instanceof Integer?"Integer":"Not Integer") );
            System.out.println();
        }
    }
}
