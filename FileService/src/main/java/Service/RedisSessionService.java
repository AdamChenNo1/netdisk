package Service;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisSessionService {

    public static String getAttr(String ID,String key){
        String sessionID = "spring:session:sessions:" +ID;
        Jedis jedis = JedisPoolUtil.getJedis();
        Boolean exists = jedis.exists(sessionID);
        if(! exists){
            return null;
        }
        String KEY = "sessionAttr:" + key;
        String VALUE = "";
        Set fields = jedis.hkeys(sessionID);

        if(fields.contains(KEY)){
            VALUE = jedis.hget(sessionID,KEY);
        }

        if(VALUE.length() > 2) {
            String value = VALUE.substring(VALUE.indexOf('\"')+1, VALUE.lastIndexOf('\"'));
            return value;
        }else {
            return null;
        }
    }
}
