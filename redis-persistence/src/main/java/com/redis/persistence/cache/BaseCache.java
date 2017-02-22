package com.redis.persistence.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface BaseCache<K, V> {

    void set(K key,V value);

    void set(K key,V value, long timeout, TimeUnit unit);

    V get(K key);

    void del(K key);

    /**  List   */

    void leftPushAll(K key, List<V> values);

    void leftPush(K key, V value);

    /**
     * 描述：rightPushAll能保证 range 取出集合与存入集合顺序相同
     *
     * @param key
     * @param values
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月19日 下午2:28:26
     */
    void rightPushAll(K key, List<V> values);

    void rightPush(K key, V value);

    V leftPop(K key);
    
    V rightPop(K key);
    
    V get(K key, Long index);

    Long size(K key);
    
    /**
     * 描述：
     *
     * @param k
     * @param start      分页第一个元素
     * @param end    分页最后一个元素   查询所有传 -1
     * @return
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月19日 下午2:14:19
     */
    List<V> range(K key, long start, long end);


    /**  Hash   */

    void hdel(K key,@SuppressWarnings("unchecked") K ... hashKeys);

    Boolean hexists(K key, K hashKey);

    V hget(K key, K hashKey);

    Map<K, V> hgetall(K key);

    void putAll(K key, Map<K, V> map);

    void put(K key, K hashKey, V value);

    Set<K> hkeys(K key);



    /**  Base    */

    Boolean exists(K key);

    void flushDb();

    void flushAll();

    void expire(K key, long timeout, TimeUnit unit);


}
