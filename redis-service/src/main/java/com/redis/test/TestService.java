package com.redis.test;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ZhangShaowei on 2017/3/20 20:01
 */
@Service("testService")
public class TestService {

    @Resource(name = "innerTestService")
    private InnerTestService innerTestService;

    public String test() throws Exception {
        return this.innerTestService.test();
    };

}
