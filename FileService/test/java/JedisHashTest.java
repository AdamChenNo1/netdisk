import Service.JedisPoolUtil;
import org.junit.Test;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisHashTest {
    private Jedis jedis = JedisPoolUtil.getJedis();

    //    hash 操作的是map对象
    //    适合存储键值对象的信息
    @Test
    //存值 参数第一个变量的名称， map键名(key)， map键值(value)
    //    调用hset
    public void fun() {
        String ID = "8548ec4d-a569-4d52-92d0-59d0c5d6ee0e";
        String sessionId = "spring:session:sessions:" + ID;
        Boolean exists = jedis.exists(sessionId);
        if(! exists){
            return;
        }
        Map<byte[] , byte[]> map = jedis.hgetAll(sessionId.getBytes());
        JdkSerializationRedisSerializer jdk = new JdkSerializationRedisSerializer();
        for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
            Object value = jdk.deserialize(entry.getValue());
            System.out.println(value.getClass() + "---" + new String(entry.getKey()) + "---" + value);
        }
    }

    @Test
    public void fun0() {
        String ID = "5f7c8cf8-7f27-44e3-8a01-fbaa9a0a8767";
        String sessionId = "spring:session:sessions:" + ID;
        Boolean exists = jedis.exists(sessionId);
        if(! exists){
            return;
        }
        String key ="sessionAttr:user";
        byte bytes[] = jedis.hget(sessionId.getBytes() , key.getBytes());
        JdkSerializationRedisSerializer jdk = new JdkSerializationRedisSerializer();
        Object value = jdk.deserialize(bytes);
        System.out.println(value);
    }

    @Test
    public void testSession(){
        String ID = "5f7c8cf8-7f27-44e3-8a01-fbaa9a0a8767";
        String sessionId = "spring:session:sessions:" + ID;
        Boolean exists = jedis.exists(sessionId);
        if(! exists){
            return;
        }
        String key ="sessionAttr:user";
        String value = jedis.hget(sessionId,key);
        System.out.println(value);
    }


    @Test
    //也可以存多个key
//    调用hmset
    public void fun1() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "caopengfei");
        map.put("age", "25");
        map.put("sex", "男");
        String res = jedis.hmset("hash2", map);
        System.out.println(res);//ok
    }

    @Test
    //获取hash中所有的值
    public void fun2() {
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
    public void testHash(){
        String key = "hash2";
        Boolean exists = jedis.exists(key);
        if(! exists){
            return;
        }
        String field1 = "username";
        String field2 = "user";

        String value1 = jedis.hget(key,field1);
        String value2 = jedis.hget(key,field2);

        System.out.println(value1);
        System.out.println(value2);

    }
    @Test
//    删除hash中的键 可以删除一个也可以删除多个，返回的是删除的个数
    public void fun3() {
        Long num = jedis.hdel("hash2", "username", "age");
        System.out.println(num);
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
    //增加hash中的键值对
    public void fun4() {
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
        jedis.hincrBy("hash2", "age", 10);
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
    //判断hash是否存在某个值
    public void fun5() {
        System.out.println(jedis.hexists("hash2", "username"));
        System.out.println(jedis.hexists("hash2", "age"));
    }

    @Test
    //获取hash中键值对的个数
    public void fun6() {
        System.out.println(jedis.hlen("hash2"));
    }

    //    获取一个hash中所有的key值
    @Test
    public void fun7() {
        Set<String> hash2 = jedis.hkeys("hash2");
        System.out.println(hash2);
    }

    //    获取所有的value值
    @Test
    public void fun8() {
        List<String> hash2 = jedis.hvals("hash2");
        System.out.println(hash2);
    }
}