package com.redis.persistence.cache;

import java.util.concurrent.TimeUnit;

import com.redis.persistence.beans.BaseBean;

public interface BaseDaoCache<T extends BaseBean> extends BaseCache<String, T> {
	
	public void save(T t);
	
	public void save(T t, long timeout, TimeUnit unit);
	
	public T get(Long id);
	
}
