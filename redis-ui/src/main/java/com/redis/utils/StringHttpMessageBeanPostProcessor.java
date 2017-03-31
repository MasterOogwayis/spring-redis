package com.redis.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.MediaType;

public class StringHttpMessageBeanPostProcessor implements BeanPostProcessor{

    private String encode;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof org.springframework.http.converter.StringHttpMessageConverter) {
            MediaType mediaType = new MediaType("text", "plain", Charset.forName(encode));
            List<MediaType> types = new ArrayList<MediaType>();
            types.add(mediaType);
            types.add(MediaType.ALL);
            ((org.springframework.http.converter.StringHttpMessageConverter) bean).setSupportedMediaTypes(types);
        }
        return bean;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }



}
