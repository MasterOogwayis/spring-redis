package com.redis.service.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.redis.persistence.domain.User;

@Service("testService")
public class TestService {


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public synchronized void println(int i) throws Exception{


        if(i == 3){
            System.err.println("i = "+ i +" sleep......");
            Thread.sleep(5000);
            System.err.println("i = "+ i +" wakeup......");
        }
        System.out.println(i);

    }


    public Long test(User user){
        System.err.println(user.getName() + ":下单ing......");
        return user.getId();
    }




}
