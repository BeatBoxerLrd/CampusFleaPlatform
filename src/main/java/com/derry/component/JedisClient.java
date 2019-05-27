package com.derry.component;

import java.util.Map;

public interface JedisClient {

    String set(String key, String value);
    String get(String key);
    Long del(String key);
    Long hset(String key, String item, String value);
    String hget(String key, String item);
    Map<String,String>  hgetAll(String key);
    Boolean hexists(String key,String item);
    String hmset(String key, Map<String,String> map);
    Long incr(String key);
    Long decr(String key);
    Long expire(String key, int second);
    Long ttl(String key);
    Long hdel(String redis_content_key, String s);
}
