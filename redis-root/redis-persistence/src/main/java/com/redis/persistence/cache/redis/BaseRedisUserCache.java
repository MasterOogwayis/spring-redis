package com.redis.persistence.cache.redis;

import org.springframework.stereotype.Repository;

import com.redis.persistence.cache.BaseUserCache;
import com.redis.persistence.cache.redis.base.BaseRedisDaoCache;
import com.redis.persistence.domain.User;

@Repository("baseRedisUserCache")
public class BaseRedisUserCache extends BaseRedisDaoCache<User> implements BaseUserCache{

}
