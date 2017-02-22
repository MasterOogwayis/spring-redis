package com.redis.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.redis.persistence.cache.BaseValueCache;
import com.redis.persistence.domain.User;

//@Aspect
//@Component
public class TestAspect {

    @Resource(name = "baseRedisCache")
    private BaseValueCache<String, Object> baseCache;


    @Pointcut("execution(public * com.redis.service.test.TestService.test(..))")
    public void declearJoinPointExpression(){}


    /**
     * 前置通知
     * @param joinPoint
     * @throws Exception
     */
    @Before("declearJoinPointExpression()") //该标签声明次方法是一个前置通知：在目标方法开始之前执行
    public void beforMethod(JoinPoint joinPoint) throws Exception{
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("this method "+methodName+" begin. param<"+ args+">");
        
        
        User user = (User)joinPoint.getArgs()[0];
        String key = "access_" + user.getId();
        
        Object v = this.baseCache.get(key);
        int times = 0;
        if(v == null){
            times = 1;
            this.baseCache.set(key, times, 1, TimeUnit.MINUTES);
        }else{
            times = Integer.valueOf(v.toString());
            if(times >= 3){
                throw new Exception("操作太频繁了，请休息下再来......");
            }
            this.baseCache.increment(key, 1);
        }
        
    }
    
    @Around("declearJoinPointExpression()") //该标签声明次方法是一个前置通知：在目标方法开始之前执行
    public void aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        User user = (User)joinPoint.getArgs()[0];
        if(user.getId() == 1l){
            joinPoint.proceed();
        }else{
            joinPoint.proceed(joinPoint.getArgs());
        }

        
    }

}
