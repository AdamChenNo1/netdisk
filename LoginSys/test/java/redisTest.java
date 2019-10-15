import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
redis测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-core.xml","classpath:/spring/spring-redis.xml"})
public class redisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JedisConnectionFactory connectionFactory;

    @Test
    public void test1(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("first","helloWorld");
        System.out.println(valueOperations.get("first"));
    }

    @Test
    public void test2(){
        ValueOperations<String,String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("second","hello Elon Chen");
        System.out.println(stringStringValueOperations.get("second"));
    }

    @Test
    public void testSessionIdKeys(){
    }
}
