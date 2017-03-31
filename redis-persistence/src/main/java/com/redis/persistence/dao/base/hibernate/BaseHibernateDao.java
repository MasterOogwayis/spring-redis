package com.redis.persistence.dao.base.hibernate;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.redis.persistence.dao.base.BaseDao;

public class BaseHibernateDao<T> implements BaseDao<T> {

    @Resource(name="hibernateTemplate")
    protected HibernateTemplate hibernateTemplate;



    @SuppressWarnings("unchecked")
    private Class<T> getClazz() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public T get(Long id) {
        return this.hibernateTemplate.get(this.getClazz(), id);
    }

    @Override
    public T get(Long id, LockMode lockMode) {
        return this.hibernateTemplate.get(this.getClazz(), id, lockMode);
    }

    @Override
    public Object get(String entityName, Long id) {
        return hibernateTemplate.get(entityName, id);
    }

    @Override
    public Long save(T entity) {
        return (Long) this.hibernateTemplate.save(entity);
    }

    @Override
    public void saveAll(final List<T> entities, final int flushSize) {
        this.hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                for(int j = 0;j<entities.size();j++){
                    session.save(entities.get(j));
                    if((j + 1) % flushSize == 0){
                        session.flush();
                        session.clear();
                    }
                }
                return null;
            }

        });

    }

    @Override
    public void update(T entity) {
        this.hibernateTemplate.update(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        this.hibernateTemplate.saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        this.hibernateTemplate.delete(entity);
    }

    @Override
    public void deleteAll(Collection<T> entities) {
        this.hibernateTemplate.deleteAll(entities);
    }


    protected List<T> find(String hql){
        return this.find(hql, -1, -1);
    }

    protected List<T> find(String hql, int maxResults){
        return this.find(hql, 0, maxResults);
    }

    @SuppressWarnings("unchecked")
    protected List<T> find(String hql, int firstResult, int maxResults, final Object ...values){

        Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        if (firstResult >= 0) {
            query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query.setMaxResults(maxResults);
        }

        return query.list();
    }



}
