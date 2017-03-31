package com.redis.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author ZhangShaowei on 2017/3/20 20:02
 */
@Aspect
@Component
public class InnerTestServiceAspect {
    /**  */
    @Pointcut("execution(public * InnerTestService.test())")
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
            return jp.proceed();
        }
    }


}
