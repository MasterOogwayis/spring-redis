package com.redis.persistence.cache;

import java.util.Set;

public interface BaseValueCache<K, V> extends BaseCache<K, V>{
	
	public Long increment(K key, long number);
	
	public Long decrement(K key, long number);
	
	public Boolean setNX(K key, V value);
	
	public V getAndSet(K key, V value);
	
	public String ping();
	
	public Set<K> keys(K pattern);

}
