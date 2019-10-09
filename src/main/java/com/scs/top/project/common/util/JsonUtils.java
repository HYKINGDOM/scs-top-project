package com.scs.top.project.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author MeasurePlatform
 * 对JSON操作类
 */
public final class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static ObjectMapper objectMapper;

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            logger.info("(Class)JsonUtils readValue is Exception.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json数组转List
     *
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            logger.info("(TypeReference)JsonUtils readValue is Exception.");
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 将 Java 对象转为 JSON 字符串
     */
    public static <T> String toJSON(T obj) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            //查询为null的字段也返回
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        }
        String jsonStr;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.info("JsonUtils toJSON is Exception.");
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /**
     * 将 JSON 字符串转为 Java 对象
     */
    public static <T> T fromJSON(String json, Class<T> type) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        T obj;
        try {
            obj = objectMapper.readValue(json, type);
        } catch (Exception e) {
            logger.info("JsonUtils fromJSON is Exception.");
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * map转换为json
     */
    public static String mapToJson(Map<String, Object> map) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.info("JsonUtils mapToJson is Exception.");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * map转换为bean
     */
    public static Object mapToObject(Map<String, String> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }
        return obj;
    }

}
