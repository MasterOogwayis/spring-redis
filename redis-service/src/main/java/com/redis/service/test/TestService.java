package com.redis.service.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.redis.persistence.domain.User;

public interface TestService {


    String test();

    default String get(){
        return this.getClass().getSimpleName();
    }


}
