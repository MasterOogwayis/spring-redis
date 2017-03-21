package com.redis.aspect;

import com.redis.exception.MyException;
import com.redis.exception.MyExceptionType;
import com.redis.persistence.cache.BaseUserCache;
import com.redis.persistence.cache.BaseValueCache;
import com.redis.persistence.domain.User;
import com.redis.service.utils.Log4jUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;


@Aspect
@Component
public class UserCacheAspect {

    private final Log log = Log4jUtils.getLog(this.getClass());

    @Resource(name = "baseRedisUserCache")
    private BaseUserCache userCache;

    @Resource(name = "baseRedisCache")
    private BaseValueCache<String, Object> baseCache;


    /**  */
    @Pointcut("execution(public * com.redis.service.*.UserService.find*All(..))")
    private void userAspect() {
        throw new UnsupportedOperationException();
    }

    /**
     * 描      述   : 方法调用前触发
     *
     * @filename: ${file_name}  CopyRight (c) ${year} Company, Inc. All rights reserved.
     * @author : zhangshaowei
     * @date : ${date} ${time}
     */
    @Before("userAspect()")
    public void before() {
        this.log.info("before......");
    }

    /**
     * 描述：方法调用前，先查询缓存。如果存在缓存，则返回缓存数据，阻止方法调用
     * 如果没有缓存，则调用业务方法，然后将结果放到缓存中
     *
     * @param jp 参数
     * @return 返回值
     * @throws Throwable 异常类
     * @author : zhangshaowei
     * @version : v1.0
     * @date : 2017年2月13日 下午2:31:53
     * @since : v1.0
     */
    @SuppressWarnings("unchecked")
    @Around("userAspect()")
    public Object cache(ProceedingJoinPoint jp) throws Throwable {
        this.log.info(jp.getTarget().getClass().getName());
        this.log.info(jp.getTarget().getClass().getSimpleName());
        // 得到方法名
        String methodName = jp.getSignature().getName();
        String key = "user_" + methodName; //key的规则
        try {
            List<User> users = this.userCache.range(key, 0, -1);
            if (CollectionUtils.isEmpty(users)) { // 缓存未命中
                log.debug("缓存未命中");
                // 调用数据库查询方法
                users = (List<User>) jp.proceed();
                this.userCache.del(key);
                this.userCache.rightPushAll(key, users);
                this.userCache.expire(key, 2, TimeUnit.HOURS);
            } else {
                log.debug(users);
            }
            return users;
        } catch (JedisConnectionException jce) {
            return jp.proceed();
        }
    }


    @After("userAspect()")
    public void after(JoinPoint jp) {
        this.log.info("after......");
    }


    /**
     * 描述：
     *
     * @param point point
     * @param ex ex
     * @author    : zhangshaowei
     * @version   : v1.0
     * @since     : v1.0
     * @date    : 2017年2月13日 下午2:21:17
     */
//    @AfterThrowing(pointcut = "userAspect()", throwing = "ex")
//    public void afterThrowing(JoinPoint point, Throwable ex) {
//        this.log.info(ex);
//        ex.printStackTrace();
//        if (ex instanceof MyException) {
//            MyException me = (MyException) ex;
//            if (me.getType() == MyExceptionType.NO_DATA) {
//                this.log.error(me.getMessage());
//            }
//        }
//
//
//    }

}
