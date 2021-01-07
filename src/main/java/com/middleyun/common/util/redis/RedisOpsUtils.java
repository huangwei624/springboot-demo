package com.middleyun.common.util.redis;

import com.middleyun.common.util.ApplicationContextHolder;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/6
 * @version 1.0
 */
public class RedisOpsUtils {

    @SuppressWarnings("unchecked")
    private static RedisTemplate<String, Object> redisTemplate
            = (RedisTemplate<String, Object>) ApplicationContextHolder.getApplicationContext()
            .getBean("redisTemplate");

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(final String key, final long timeout, final TimeUnit unit) {
        Boolean ret = redisTemplate.expire(key, timeout, unit);
        return ret != null && ret;
    }

    /**
     * 删除单个key
     *
     * @param key 键
     * @return true=删除成功；false=删除失败
     */
    public static boolean del(final String key) {
        Boolean ret = redisTemplate.delete(key);
        return ret != null && ret;
    }

    /**
     * 删除多个key
     *
     * @param keys 键集合
     * @return 成功删除的个数
     */
    public static long del(final Collection<String> keys) {
        Long ret = redisTemplate.delete(keys);
        return ret == null ? 0 : ret;
    }

    /**
     * 存入普通对象, 不设置过期时间
     *
     * @param key Redis键
     * @param value 值
     */
    public static void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置普通对象，默认超时时间为秒
     * @param key
     * @param value
     * @param timeout
     */
    public static void set(final String key, final Object value, final long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 存入普通对象
     *
     * @param key 键
     * @param value 值
     * @param timeout 有效期，单位秒
     */
    public static void set(final String key, final Object value, final long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public static Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取指定前缀的对象
     * @param keyPrefix 固定前缀的key
     * @return
     */
    public static List<Object> prefixGet(final String keyPrefix) {
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        if (keys == null || keys.size() == 0) {
            return null;
        }
        List<Object> objects = new ArrayList<>();
        keys.forEach(key ->{
            objects.add(get(key));
        });
        return objects;
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public static void hPut(final String key, final String hKey, final Object value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 往Hash中存入多个数据
     *
     * @param key Redis键
     * @param values Hash键值对
     */
    public static void hPutAll(final String key, final Map<String, Object> values) {
        redisTemplate.opsForHash().putAll(key, values);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public static Object hGet(final String key, final String hKey) {
        return redisTemplate.opsForHash().get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public static List<Object> hMultiGet(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 往Set中存入数据
     *
     * @param key Redis键
     * @param values 值
     * @return 存入的个数
     */
    public static long sSet(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 删除Set中的数据
     *
     * @param key Redis键
     * @param values 值
     * @return 移除的个数
     */
    public static long sDel(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入数据
     *
     * @param key Redis键
     * @param value 数据
     * @return 存入的个数
     */
    public static long lPush(final String key, final Object value) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public static long lPushAll(final String key, final Collection<Object> values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public static long lPushAll(final String key, final Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 从List中获取begin到end之间的元素
     *
     * @param key Redis键
     * @param start 开始位置
     * @param end 结束位置（start=0，end=-1表示获取全部元素）
     * @return List对象
     */
    public static List<Object> lGet(final String key, final int start, final int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }


}
