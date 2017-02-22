package com.redis.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redis.persistence.dao.UserDao;
import com.redis.persistence.domain.User;


@Service(UserService.SERVICE_NAME)
@Transactional(rollbackFor = Exception.class)
public class UserService {
    
    public static final String SERVICE_NAME = "userService";
    
    @Resource(name = "userHibernateDao")
    private UserDao<User> userHibernateDao;
    
    @Cacheable(value = "redisCache", key = "'user:' + #id", condition = "null != #id") 
    //@CachePut(value = "redisCache", key = "'User_' + #id", condition = "null != #id")每次都取方法返回值放入缓存
    public User get(Long id) {
        return this.userHibernateDao.get(id);
    }

//    @CacheEvict(value = "redisCache", key = "'user:' + #user.getId()", condition = "#user != null")
    @Caching(evict = {
            @CacheEvict(value = "redisCache", key = "'user:' + #user.getId()", condition = "#user != null"),
            @CacheEvict(value = "listCache", key = "'list:user'")})
    public Long save(User user) {
        return this.userHibernateDao.save(user);
    }
    
    /**
     * 描述：触发删除缓存
     *
     * @param user
     * @author  : zhangshaowei
     * @version : v1.0
     * @since   : v1.0
     * @date    : 2017年2月15日 下午1:49:37
     */
    @CacheEvict(value = "redisCache", key="'user:' + #user.getId()")
    public void update(User user) {
        this.userHibernateDao.saveOrUpdate(user);
    }
    
    @Cacheable(value = "listCache", key = "'list:user'")
    public List<User> findAll() {
        return this.userHibernateDao.findAll();
    }
    
    @Cacheable(value = "listCache", key = "#key")
    public List<String> testList(String key){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        return list;
    }
    
}    
