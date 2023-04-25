package com.visual.face.search.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    /**
     * 将Bean转化为json字符串
     *
     * @param obj bean对象
     * @return json
     */
    public static String toString(Object obj) {
        return toString(obj, false, false);
    }

    public static String toSimpleString(Object obj) {
        return toString(obj, false, true);
    }

    /**
     * 将Bean转化为json字符串
     *
     * @param obj          bean对象
     * @param prettyFormat 是否格式化
     * @return json
     */
    public static String toString(Object obj, boolean prettyFormat, boolean noNull) {
        if (prettyFormat) {
            if (noNull) {
                return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat);
            } else {
                return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat);
            }
        } else {
            if (noNull) {
                return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
            } else {
                return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.DisableCircularReferenceDetect);
            }
        }
    }


    /**
     * 将字符串转换为Entity
     *
     * @param json  数据字符串
     * @param clazz Entity class
     * @return
     */
    public static <T> T toEntity(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将字符串转换为Entity
     *
     * @param json          数据字符串
     * @param typeReference Entity class
     * @return
     */
    public static <T> T toEntity(String json, TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * 将字符串转换为Map
     *
     * @param json 数据字符串
     * @return Map
     */
    public static Map<String, Object> toMap(String json) {
        return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * 将字符串转换为List<T>
     *
     * @param json            数据字符串
     * @param collectionClass 泛型
     * @return list<T>
     */
    public static <T> List<T> toList(String json, Class<T> collectionClass) {
        return JSON.parseArray(json, collectionClass);
    }

    /**
     * 将字符串转换为List<Map<String, Object>>
     *
     * @param json 数据字符串
     * @return list<map>
     */
    public static List<Map<String, Object>> toListMap(String json) {
        return JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    /**
     * 将字符串转换为Object
     *
     * @param json 数据字符串
     * @return list<map>
     */
    public static JSONObject toJsonObject(String json) {
    	return JSON.parseObject(json);
    }
    
    /**
     * 将字符串转换为Array
     *
     * @param json 数据字符串
     * @return list<map>
     */
    public static JSONArray toJsonArray(String json) {
    	return JSON.parseArray(json);
    }
    
}

