package com.iomc.common.redis.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private static RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效，-2代表键不存在
     */
    public static long getExpireTime(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (expire != null) {
            return expire;
        }
        return -2;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key        键
     * @param expireTime 时间(秒)
     */
    public static void setExpireTime(String key, long expireTime) {
        try {
            if (expireTime > 0) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除指定 key 的过期时间
     *
     * @param key 键
     */
    public static void removeExpireTime(String key) {
        redisTemplate.boundValueOps(key).persist();
    }

    /**
     * 获取缓存中所有的键
     *
     * @param key 键
     * @return 缓存中所有的键
     */
    public static Set keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * value增加值
     *
     * @param key    键
     * @param number 增加的值
     * @return 返回增加后的值
     */
    public static Long incrBy(String key, long number) {
        return redisTemplate.opsForValue().increment(Arrays.toString(key.getBytes()), number);
    }

    /**
     * value减少值
     *
     * @param key    键
     * @param number 减少的值
     * @return 返回减少后的值
     */
    public static Long decrBy(String key, long number) {
        return redisTemplate.opsForValue().decrement(Arrays.toString(key.getBytes()), number);
    }

    /**
     * 根据key获取value
     *
     * @param key 键
     * @return 返回值
     */
    public static Object get(String key) {
        BoundValueOperations<String, Object> boundValueOperations = redisTemplate.boundValueOps(key);
        return boundValueOperations.get();
    }


    /**
     * 根据key删除缓
     *
     * @param keys 键
     */
    public static void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 根据key删除缓
     *
     * @param key 键
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        set(key, value, 0);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public static void set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描缓存
     *
     * @param key 模糊查询 user:info:*
     */
    public static Set scan(String key) {
        ScanOptions options = ScanOptions.scanOptions().match(key).build();
        Set<String> result = new HashSet<>();

        Cursor<byte[]> cursor =
                Objects.requireNonNull(redisTemplate.getConnectionFactory())
                        .getConnection()
                        .scan(options);
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        return result;
    }
}
