package com.redis.service.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FormConverter {


    protected static Log log = Log4jUtils.getLog(FormConverter.class);

    protected Gson gson;

    /**
     * 描述：构造函数
     */
    public FormConverter() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    /**
     * 描述：构造函数
     * @param gson 自定义了数据转换格式的Gson
     */
    public FormConverter(Gson gson) {
        this.gson = gson;
    }

    /**
     * 描述：Form表单转成gson字符串<br>
     * 支持数组和List，表单属性会去掉前后空格，空字符串按null处理
     * @param request request
     * @param clazz 要转换的类模板
     * @return gson字符串
     * @author lurf
     * @version 2.0.0
     * @since 2.0.0
     */
    public String convertToGson(HttpServletRequest request, Class<?> clazz) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<?> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String key = enu.nextElement().toString();
            if ("".equals(key)) continue;

            if ("_method".equals(key)) continue; // 排除spring restful 使用的_method

            String key1 = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
            String key2 = "is" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());

            String[] values = request.getParameterValues(key);
            try {
                String typeName = null;
                try {
                    typeName = clazz.getMethod(key1).getReturnType().getName();
                } catch (NoSuchMethodException e) {
                    // 如果查找get的方法失败，再查找is的方法，如果都失败抛出异常
                    typeName = clazz.getMethod(key2).getReturnType().getName();
                }
                if (typeName.indexOf("[") != -1 || typeName.indexOf("List") != -1) {
                    List<String> valuesList = Arrays.asList(values);
                    List<String> newValues = null;
                    for (int i=0; i<valuesList.size(); i++) {
                        if (!"".equals(valuesList.get(i).trim())) {
                            if (null == newValues) {
                                newValues = new ArrayList<String>();
                            }
                            newValues.add(valuesList.get(i).trim());
                        }
                    }
                    map.put(key, newValues);
                } else {
                    map.put(key, values[0].equals("") ? null : values[0].trim());
                }
            } catch (SecurityException e) {
                log.warn("获取 " + clazz.getName() + " 类的 " + key + " 属性出现[" + e.toString() + "]异常");
            } catch (NoSuchMethodException e) {
                log.warn("获取 " + clazz.getName() + " 类的 " + key + " 属性出错，请检查此属性是否有对应的getter，setter方法");
            }
        }
        String jsonBody = gson.toJson(map);
        log.debug("Form表单序列化后的Gson字符串为：" + jsonBody);
        return jsonBody;
    }

    /**
     * 描述：Form表单转成对象<br>
     * 支持数组和List，表单属性会去掉前后空格，空字符串按null处理
     * @param request request
     * @param clazz 要转换成的对象类
     * @return 转换后的对象
     * @author lurf
     * @version 2.0.0
     * @since 2.0.0
     */
    public <T> T convertToBean(HttpServletRequest request, Class<T> clazz) {
        String jsonBody = convertToGson(request, clazz);
        return gson.fromJson(jsonBody, clazz);
    }


}
