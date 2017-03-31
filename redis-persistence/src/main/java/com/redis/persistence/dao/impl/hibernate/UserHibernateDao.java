package com.redis.persistence.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.redis.persistence.dao.UserDao;
import com.redis.persistence.dao.base.hibernate.BaseHibernateDao;
import com.redis.persistence.domain.User;


@Repository("userHibernateDao")
public class UserHibernateDao extends BaseHibernateDao<User> implements UserDao<User> {

    @Override
    public List<User> findAll() {
        return this.find("from User");
    }

    @Override
    public Integer get() {
        String hql = "select COALESCE(sum(u.age),0) from User u where u.id = " + 100;
        Query query = this.hibernateTemplate.getSessionFactory().getCurrentSession()
                .createQuery(hql);
        return ((Long) query.list().get(0)).intValue();
    }

}
