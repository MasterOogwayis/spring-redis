package test;

import com.redis.service.user.UserService;
import org.junit.*;

import javax.annotation.Resource;

/**
 * Created by ZhangShaowei on 2017/2/17.
 */
public class AspectTest {

    @Resource(name = UserService.SERVICE_NAME)
    private UserService userService;

    @org.junit.Test
    public void test() {
        String str = "earth";
        System.out.println();
        for (int i = 0;i<10;i++) {



        }

    }




}
