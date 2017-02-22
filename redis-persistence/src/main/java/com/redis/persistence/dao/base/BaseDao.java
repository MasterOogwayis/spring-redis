package com.redis.persistence.dao.base;

import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;

public interface BaseDao<T> {

    /**
     * 描述：通过对象标识获取对象
     * @param id 对象标识
     * @return 实体对象
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public T get(Long id);

    /**
     * 描述：通过对象标识获取对象， 同时锁住该对象对应的行
     * @param id 对象标识
     * @param lockMode 锁模式
     * @return 实体对象
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public T get(Long id, LockMode lockMode);

    /**
     * 描述：通过对象标识获取对象
     * @param entityName 对象类型名
     * @param id 对象标识
     * @return 实体对象
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public Object get(String entityName, Long id);

    /**
     * 描述：保存实体对象
     * @param entity 实体对象
     * @return 对象标识
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public Long save(T entity);

    /**
     * 描述：批量保存实体对象
     * @param entities 实体集
     * @param flushSize 刷新大小
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public void saveAll(List<T> entities, int flushSize);

    /**
     * 描述：更新实体对象
     * @param entity 实体对象
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public void update(T entity);

    /**
     * 描述：保存或更新实体对象
     * @param entity 实体对象
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public void saveOrUpdate(T entity);

    /**
     * 描述：删除实体对象
     * @param entity 实体对象
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public void delete(T entity);

    /**
     * 描述：批量删除实体对象
     * @param entities 实体集
     * @author lurf
     * @version 2.0.0 2012-8-31
     * @since 2.0.0
     */
    public void deleteAll(Collection<T> entities);

}
