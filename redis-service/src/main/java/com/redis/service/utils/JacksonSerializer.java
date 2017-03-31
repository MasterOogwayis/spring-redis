package com.redis.service.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * @author ZhangShaowei on 2017/3/13 17:54
 */

public class JacksonSerializer {

    /**
     * jackson的json转换器
     */
    protected ObjectMapper mapper = new ObjectMapper();

    /**
     * 初始化mapper参数
     */
    public JacksonSerializer() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //忽略没有的属性
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //允许没有双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true); //允许转义字符
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //允许单引号
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * jackson 获取集合对象类型
     *
     * @param collectionClass List ...
     * @param elementClasses  Object.class
     * @return JavaType
     */
    protected JavaType getCollectionType(final Class<? extends Collection> collectionClass, final Class<?> elementClasses) {
        return this.mapper.getTypeFactory().constructCollectionType(collectionClass, elementClasses);

    }

}
