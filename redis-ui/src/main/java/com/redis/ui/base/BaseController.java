package com.redis.ui.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redis.service.utils.FormConverter;

public class BaseController {

    protected Gson gson = new GsonBuilder().create();

    private FormConverter formConverter = new FormConverter(gson);






    @ExceptionHandler(Exception.class)
    protected void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response){
        e.printStackTrace();
        Map<String,Object> message = new HashMap<>();
        message.put("message", e.getMessage());

    }





    /**
     * 描述：请求参数转对象
     *
     * @param request
     * @param clazz
     * @return
     * @author     : zhangshaowei
     * @version    : v1.0
     * @since     : v1.0
     * @date     : 2016年12月21日 上午10:50:51
     */
    protected <T> T convertToBean(HttpServletRequest request,Class<T> clazz){
        return this.formConverter.convertToBean(request, clazz);
    }


}
