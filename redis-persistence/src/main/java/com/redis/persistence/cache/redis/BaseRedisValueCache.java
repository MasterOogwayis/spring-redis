package com.redis.persistence.cache.redis;

import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.redis.persistence.cache.BaseValueCache;
import com.redis.persistence.cache.redis.base.BaseRedisCache;

@Repository("baseRedisCache")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BaseRedisValueCache extends BaseRedisCache<String, Object> implements BaseValueCache<String, Object>{
	
	/**
	 * 描述：开启事物后 redis的incr并不会立即返回 
	 * 		redis的事物是一队指令集 
	 * 		只有在spring事物成功提交后才会提交运行
	 *
	 * @param key
	 * @return
	 * @author 	: zhangshaowei
	 * @version	: v1.0
	 * @since 	: v1.0
	 * @date 	: 2017年1月10日 上午9:10:12
	 */
	@Override
	public Long increment(String key, long number){
		return this.valueOps.increment(key, number);
	}
	
	@Override
	public Long decrement(String key, long number){
		return this.valueOps.increment(key, -number);
	}
	
	
	/**
	 * 描述：用于实现 分布式锁
	 * 
	 *	   1. setNX true
	 * 	   2. expire 设置超时时间
	 * 
	 * 	       其他线程or实例 	1. setNx false
	 * 					2. 等待随机时间继续请求锁
	 *					3. 超出等待时间则中断
	 * PS : 当缓存服 gg 后被穿透 则采用直接锁数据库的方式
	 * 		然后尝试继续吧新数据写入缓存
	 * @param key
	 * @param value
	 * @return
	 * @author 	: zhangshaowei
	 * @version	: v1.0
	 * @since 	: v1.0
	 * @date 	: 2017年1月11日 下午1:26:52
	 */
	@Override
	public Boolean setNX(String key, Object value){
		return this.valueOps.setIfAbsent(key, value);
	}
	
	
	@Override
	public Object getAndSet(String key, Object value){
		return this.valueOps.getAndSet(key, value);
	}
	
	
	@Override
	public String ping(){
		return this.redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.ping();
			}
		});
	}

	@Override
	public Set<String> keys(String pattern) {
		return this.redisTemplate.keys(pattern);
	}
	
}
