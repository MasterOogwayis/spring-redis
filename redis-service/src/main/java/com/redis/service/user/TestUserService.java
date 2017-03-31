package com.redis.service.user;

import com.redis.persistence.dao.UserDao;
import com.redis.persistence.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("testUserService")
@Transactional(rollbackFor = Exception.class)
public class TestUserService {

    @Resource(name = "userHibernateDao")
    private UserDao<User> userDao;


    public void saveOrUpdate(User user) {
        this.userDao.saveOrUpdate(user);
    }

    @Cacheable(value = "OC", key = "'user:' + #id")
    public User get(Long id) {
        return this.userDao.get(id);
    }

    public List<User> findAll() {
        return this.userDao.findAll();
    }

    public void test() {
        System.out.println(this.userDao.get());
    }


}
