package com.redis.persistence.cache.redis.base;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.redis.persistence.cache.BaseCache;



/**
 * 描述：redis key-value cache
 * 
 *         包含主要数据类型操作  key-value list hash
 *         非常用数据结构暂无 扩充请参考spring-data-redis api
 *
 * @author zhangshaowei
 * @version v1.0
 * @since v1.0
 */
public class BaseRedisCache<K, V> implements BaseCache<K, V>{

    @Resource(name = "redisTemplate")
    protected RedisTemplate<K,V> redisTemplate;

    @Resource(name = "redisTemplate")
    protected ValueOperations<K, V> valueOps;

    @Resource(name = "redisTemplate")
    protected ListOperations<K, V> listOps;

    @Resource(name = "redisTemplate")
    protected HashOperations<K, K, V> hashOps;



    /**
     * 描述：不设置缓存 失效时间是非常危险的
     *
     * @param key
     * @param value
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月10日 下午3:32:01
     */
    @Override
    public void set(K key,V value){
        this.valueOps.set(key, value);
    }


    /**
     * 描述：
     *
     * @param key        键
     * @param value        值
     * @param timeout    有效时间
     * @param unit        时间类型
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月5日 下午3:45:56
     */
    @Override
    public void set(K key,V value, long timeout, TimeUnit unit){
        this.valueOps.set(key, value, timeout, unit);
    }

    @Override
    public V get(K key){
        return this.valueOps.get(key);
    }

    @Override
    public void del(K key){
        this.redisTemplate.delete(key);
    }

    @Override
    public void leftPushAll(K key, List<V> values){
        this.listOps.leftPushAll(key, values);
    }

    @Override
    public void leftPush(K key, V value){
        this.listOps.leftPush(key, value);
    }

    @Override
    public void rightPushAll(K key, List<V> values){
        this.listOps.rightPushAll(key, values);
    }

    @Override
    public void rightPush(K key, V value){
        this.listOps.rightPush(key, value);
    }

    @Override
    public V rightPop(K key) {
        return this.listOps.rightPop(key);
    }
    
    @Override
    public V leftPop(K key) {
        return this.listOps.leftPop(key);
    }

    @Override
    public V get(K key, Long index) {
        return this.listOps.index(key, index);
    }
    
    @Override
    public Long size(K key) {
        return this.listOps.size(key);
    }
    
    @Override
    public List<V> range(K key, long start, long end){
        return this.listOps.range(key, start, end);
    }


    @Override
    public void hdel(K key, @SuppressWarnings("unchecked") K ... hashKeys) {
        this.hashOps.delete(key, new Object[]{hashKeys});
    }

    @Override
    public Boolean hexists(K key, K hashKey){
        return this.hashOps.hasKey(key, hashKey);
    }

    @Override
    public V hget(K key, K hashKey){
        return this.hashOps.get(key, hashKey);
    }

    @Override
    public void putAll(K key, Map<K, V> map){
        this.hashOps.putAll(key, map);
    }

    @Override
    public void put(K key, K hashKey, V value){
        this.hashOps.put(key, hashKey, value);
    }

    @Override
    public Map<K, V> hgetall(K key) {
        return this.hashOps.entries(key);
    }


    @Override
    public Set<K> hkeys(K key) {
        return this.hashOps.keys(key);
    }



    @Override
    public Boolean exists(K key){
        return this.redisTemplate.hasKey(key);
    }

    /**
     * 描述：清空缓存----当前缓存库
     *
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月6日 下午4:21:09
     */
    @Override
    public void flushDb(){
        this.redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    /**
     * 描述：清空缓存----包含所有集群的从库
     *
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月6日 下午4:21:05
     */
    @Override
    public void flushAll(){
        this.redisTemplate.getConnectionFactory().getConnection().flushAll();
    }


    /**
     * 描述：设置超时
     *
     * @param key
     * @param timeout
     * @param unit
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月11日 下午1:35:44
     */
    @Override
    public void expire(K key, long timeout, TimeUnit unit){
        this.redisTemplate.expire(key, timeout, unit);
    }

}
