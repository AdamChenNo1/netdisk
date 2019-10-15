package Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class JedisPoolUtil {
    private static JedisPool pool = null;
    static {
        //加载配置文件
        Map pro = PropertiesFileUtil.getProperties("Redis.properties");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxTotal(Integer.parseInt((String) pro.get("redis.maxTotal")));
        //最大空闲连接数
        poolConfig.setMaxIdle(Integer.parseInt((String) pro.get("redis.maxIdle")));
        //最小空闲连接数
        poolConfig.setMinIdle(Integer.parseInt((String) pro.get("redis.minIdle")));
        pool = new JedisPool(poolConfig,
                            (String)pro.get("redis.host"),
                Integer.parseInt((String) pro.get("redis.port")),
                Integer.parseInt((String) pro.get("redis.timeout")),
                            (String) pro.get("redis.password"));
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void release(Jedis jedis){
        if(null != jedis){
            jedis.close();
        }
    }
}