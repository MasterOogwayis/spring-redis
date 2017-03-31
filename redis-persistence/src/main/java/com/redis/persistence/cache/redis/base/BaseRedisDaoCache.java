package com.redis.persistence.cache.redis.base;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import com.redis.persistence.beans.BaseBean;
import com.redis.persistence.cache.BaseDaoCache;

public class BaseRedisDaoCache<T extends BaseBean> extends BaseRedisCache<String, T> implements BaseDaoCache<T>{

    @SuppressWarnings("unchecked")
    private Class<T> getClazz() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }



    /**
     * 描述：永久缓存 不建议使用
     *
     */
    @Override
    public void save(T t) {
        String key = getClazz().getName() + "_" + t.getId();
        super.set(key, t);
    }

    /**
     * 描述：默认 key   ClassName + _ + id
     *
     * @param t
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月5日 下午4:33:46
     */
    @Override
    public void save(T t, long timeout, TimeUnit unit) {
        String key = getClazz().getName() + "_" + t.getId();
        super.set(key, t, timeout, unit);
    }


    /**
     * 描述：获取缓存中对应ID的对象
     *         key 规则 包路径+类名+_+id
     *
     * @param id
     * @return
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2017年1月6日 上午11:44:46
     */
    @Override
    public T get(Long id) {
        String key = getClazz().getName() + "_" + id;
        return super.valueOps.get(key);
    }

}
