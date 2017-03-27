package com.redis.test;

import org.springframework.stereotype.Service;

/**
 * @author ZhangShaowei on 2017/3/20 20:01
 */
@Service("innerTestService")
public class InnerTestService {

    public String test() throws Exception {
        if (1 == 1) {
            throw new Exception("抛个异常......");
        }
        return "InnerTestService";
    };

}
