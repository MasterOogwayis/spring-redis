package com.redis.test;

import com.redis.persistence.domain.User;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangShaowei on 2017/3/20 20:02
 */
@Aspect
@Component
public class TestServiceAspect {
    /**  */
    @Pointcut("execution(public * TestService.test())")
    private void userAspect() {
        //@Pointcut methods: void return type, no throws clause, empty method body
    }


    /**
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("userAspect()")
    public Object cache(ProceedingJoinPoint jp) throws Throwable {
        try {
            return jp.proceed();
        } catch (Exception jce) {
            System.err.println("123123");
            return jce.getMessage();
        }
    }


}
