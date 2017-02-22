package com.redis.service.cacheable;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.StringUtils;

import com.redis.persistence.cache.BaseValueCache;

public class RedisCache implements Cache{

    @Resource(name = "baseRedisCache")
    private BaseValueCache<String, Object> baseCache;

    
    
    public BaseValueCache<String, Object> getBaseCache() {
        return baseCache;
    }

    public void setBaseCache(BaseValueCache<String, Object> baseCache) {
        this.baseCache = baseCache;
    }

    /** 
     * 缓存名称 
     */ 
    private String name;

    /**
     * 超时时间 
     */  
    private Long timeout = 1000L;

    public void setName(String name) {
        this.name = name;    
    }  

    @Override
    public String getName() {
        return this.name;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public Object getNativeCache() {
        return this.baseCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object object = this.baseCache.get(key.toString());
        return (object != null ? new SimpleValueWrapper(object) : null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        if (StringUtils.isEmpty(key) || null == type) {
            return null;  
        } else {  
            Object object = this.baseCache.get(key.toString());    
            if (type.isInstance(object) && null != object) {  
                return (T) object;  
            } else {  
                return null;  
            }  
        } 
    }

    @Override
    public void put(Object key, Object value) {System.err.println("put");
        this.baseCache.set(key.toString(), value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void evict(Object key) {
        this.baseCache.del(key.toString());
    }

    @Override
    public void clear() {
        this.baseCache.flushDb();
    }

}
