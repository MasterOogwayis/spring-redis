package com.redis.manager;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;

/**
 * Created by ZhangShaowei on 2017/2/28.
 */
public class ExtendsRedisCacheManager extends RedisCacheManager {

    private String defaultCacheName;

    public void setDefaultCacheName(String defaultCacheName) {
        this.defaultCacheName = defaultCacheName;
    }


    public ExtendsRedisCacheManager(RedisOperations redisOperations) {
        super(redisOperations);
    }

    public ExtendsRedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames) {
        super(redisOperations, cacheNames);
    }

    @Override
    public Cache getCache(String name) {
        if(StringUtils.isBlank(name)){
            name = defaultCacheName;
        }
        return super.getCache(name);
    }





}
