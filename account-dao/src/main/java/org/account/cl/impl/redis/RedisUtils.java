package org.account.cl.impl.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
public class RedisUtils<K, V> {

    private RedisTemplate<K, V> redisTemplate;

    public RedisUtils(RedisTemplate<K, V> redisTemplate) {
        if (redisTemplate == null) {
            throw new RuntimeException("Redis 链接无效");
        }
        this.redisTemplate = redisTemplate;
    }

    public void expire(K k, int second) {
        this.redisTemplate.expire(k, second, TimeUnit.SECONDS);
    }

    public boolean set(K key, V value) {
        getStringObj().set(key, value);
        return true;
    }

    public boolean set(K key, V value, long second) {
        getStringObj().set(key, value, second, TimeUnit.SECONDS);
        return true;
    }

    public V get(K key, Class<V> c) {
        String value = String.valueOf(getStringObj().get(key));
        if (value == null) {
            return null;
        }
        return JSON.parseObject(value, c);
    }

    private ValueOperations<K, V> getStringObj() {
        return Optional.ofNullable(redisTemplate.opsForValue()).orElseThrow(()->new RuntimeException("取得String类型的redis实例出错")) ;
    }

    public boolean zadd(K key, V value, double score) {
        return getZSetObj().add(key, value, score);
    }

    public long zrem(K key, V value) {
        return getZSetObj().remove(key, value);
    }

    public Set<V> zrangeAll(K key) {
        return getZSetObj().range(key, 0L, -1L);
    }

    private ZSetOperations<K, V> getZSetObj() {
        return Optional.ofNullable(redisTemplate.opsForZSet()).orElseThrow(()->new RuntimeException("取得String类型的redis实例出错")) ;
    }
}
