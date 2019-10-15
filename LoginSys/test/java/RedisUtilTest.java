import elon.mapper.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.text.html.HTMLDocument;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-core.xml","classpath:/spring/spring-redis.xml"})
public class RedisUtilTest {
    @Test
    public void test1(){
        RedisUtil.valueSet("hello","elon");
        Object out = RedisUtil.valueGet("hello");
        System.out.println(out);
    }

    @Test
    public void test2(){
        RedisUtil.valueSet("hello","elon");
        Object out = RedisUtil.valueGet("hello");
        System.out.println(out);
    }

    @Test
    public void test3(){
        String sessionId = "spring:session:sessions:5266c8a8-dec2-4b27-a63c-b5c7b8284892";
        Set s = RedisUtil.hashKeys(sessionId);
        Iterator it = s.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
        List v = RedisUtil.hashValues(sessionId);
        Iterator vt = v.iterator();
        while (vt.hasNext())
            System.out.println(vt.next());
    }

}
