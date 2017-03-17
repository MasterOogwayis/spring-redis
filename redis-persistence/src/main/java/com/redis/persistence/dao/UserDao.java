package com.redis.persistence.dao;

import java.util.List;

import com.redis.persistence.dao.base.BaseDao;
import com.redis.persistence.domain.User;

public interface UserDao<T> extends BaseDao<User> {

    List<User> findAll();

    Integer get();

}
