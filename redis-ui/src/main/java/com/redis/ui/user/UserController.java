package com.redis.ui.user;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redis.persistence.cache.BaseValueCache;
import com.redis.persistence.domain.User;
import com.redis.service.test.TestService;
import com.redis.service.user.UserService;
import com.redis.ui.base.BaseController;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    
    @Resource(name = "userService")
    private UserService userService;
    
    @Resource(name = "testService")
    private TestService testService;
    
    
    @Resource(name = "baseRedisCache")
    private BaseValueCache<String, Object> baseCache;
    
    @RequestMapping
    public @ResponseBody String test(@RequestParam Integer i) throws Exception{
        Boolean locked = this.baseCache.setNX("lock", "locked");
        if(locked){
            this.baseCache.expire("lock", 100, TimeUnit.SECONDS);
        }
        this.testService.println(1);
        return String.valueOf(locked);
    }
    
    @RequestMapping("/add")
    public @ResponseBody Object add(HttpServletRequest request) throws Exception{
        User user = this.convertToBean(request, User.class);
        userService.save(user);
        
        Map<String,Object> message = new HashMap<>();
        message.put("message", "保存成功");
        message.put("data", user);
        return message;
    }
    
    
    
    @RequestMapping("/get")
    public @ResponseBody Object get(@RequestParam Long id) throws Exception{
        User user = this.userService.get(id);
        return user;
    }
    
    
    
    
}
