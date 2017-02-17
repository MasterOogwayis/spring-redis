package com.redis.persistence.dao.impl.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redis.persistence.dao.UserDao;
import com.redis.persistence.dao.base.hibernate.BaseHibernateDao;
import com.redis.persistence.domain.User;



@Repository("userHibernateDao")
public class UserHibernateDao extends BaseHibernateDao<User> implements UserDao<User>{

	@Override
	public List<User> findAll() {
		return this.find("from User");
	}
	
}
