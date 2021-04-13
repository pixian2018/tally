package com.ztg.demo1.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 基于fastjson封装的json转换工具类 (大量数据用)
 * @author: zhoutg
 * @time: 2018/9/4 12:20
 */
public class FastJsonUtils {

    /**
     * 功能描述：把JSON数据转换成指定的java对象
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     * @return 指定的java对象
     */
    public static <T> T getJsonToBean(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把java对象转换成JSON数据
     *
     * @param object java对象
     * @return JSON数据
     */
    public static String getBeanToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     * @return List<T>
     */
    public static <T> List<T> getJsonToList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
     *
     * @param jsonData JSON数据
     * @return List<Map       <       String       ,               Object>>
     */
    public static List<Map<String, Object>> getJsonToListMap(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    /***
     * 解析为列表
     *
     * @param jsonString
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> getJsonToListByKey(String jsonString, String key, Class<T> t) {
        ArrayList<T> list = new ArrayList<T>();
        if (StringUtil.isNotBlank(jsonString)) {
            try {
                JSONObject jsonObj = JSONObject.parseObject(jsonString);
                JSONArray inforArray = jsonObj.getJSONArray(key);
                for (int index = 0; index < inforArray.size(); index++) {
                    list.add(JSONObject.toJavaObject(
                            inforArray.getJSONObject(index), t));
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    /**
     * 把json转成map
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> getJsonToMap(String jsonString) {
        JSONObject object = JSONObject.parseObject(jsonString);
        return object.getInnerMap();
    }
}
