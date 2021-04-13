package com.ztg.demo1.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 实体对象工具类
 * @author: gaodm
 * @time: 2018/9/13 20:12
 */
@Slf4j
public class EntityUtil {

    /**
     * 将list中的元素放到Map<K, List<V>> 以建立 key - List<value> 索引
     *
     * @param list         List<V> 元素列表
     * @param keyFieldName String 元素的属性名称, 该属性的值作为索引key
     * @param <K>          key类型
     * @param <V>          value类型
     * @return Map<K       ,               V> key - value 索引
     */
    public static <K, V> Map<K, List<V>> makeEntityListMap(List<V> list, String keyFieldName) {
        Map<K, List<V>> map = new LinkedHashMap<>();
        if (list == null || list.size() == 0) {
            return map;
        }
        try {
            Method getter = getMethod(list.get(0).getClass(), keyFieldName, "get");
            for (V item : list) {
                @SuppressWarnings("unchecked")
                K key = (K) getter.invoke(item);
                List<V> groupList = map.get(key);
                if (groupList == null) {
                    groupList = new ArrayList<>();
                    map.put(key, groupList);
                }
                groupList.add(item);
            }
        } catch (Exception e) {
            log.error("makeEntityListMap error list is " + list, e);
            return map;
        }
        return map;
    }

    /**
     * 将list中的元素放到Map<K, V>以建立 key - value 索引，【keyFieldName 一般唯一，否则后面会覆盖前面的数据】
     *
     * @param list         List<V> 元素列表
     * @param keyFieldName String 元素的属性名称, 该属性的值作为索引key,
     * @param <K>          key类型
     * @param <V>          value类型
     * @return Map<K       ,               V> key - value 索引
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> makeEntityMap(List<V> list, String keyFieldName) {
        Map<K, V> map = new HashMap<>();
        if (list == null || list.size() == 0) {
            return map;
        }
        try {
            Method getter = getMethod(list.get(0).getClass(), keyFieldName, "get");
            for (V item : list) {
                map.put((K) getter.invoke(item), item);
            }
        } catch (Exception e) {
            log.error("makeEntityMap error list is " + list, e);
            return map;
        }
        return map;
    }

    /**
     * 将list中的元素放到Map<M, N>以建立 key - value 索引
     * modified from com.tqmall.saint.biz.util.EntityUtil#makeEntityMap(java.util.List, java.lang.String)
     *
     * @param collection     Collection<V> 元素列表
     * @param keyFieldName   String 元素的属性名称, 该属性的值作为Map的key
     * @param valueFieldName String 元素的属性名称, 该属性的值作为Map的value
     * @param <M>            key类型
     * @param <N>            value类型
     * @param <V>            列表元素类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <M, N, V> Map<M, N> makeMapWithKeyValue(Collection<V> collection, String keyFieldName, String valueFieldName) {
        Map<M, N> map = new HashMap<>();
        if (collection == null || collection.isEmpty()) {
            return map;
        }
        Iterator<V> it = collection.iterator();
        boolean isFirst = true;
        Method keyGetter = null;
        Method valueGetter = null;
        try {
            while (it.hasNext()) {
                V o = it.next();
                if (isFirst) {
                    keyGetter = getMethod(o.getClass(), keyFieldName, "get");
                    valueGetter = getMethod(o.getClass(), valueFieldName, "get");
                    isFirst = false;
                }
                map.put((M) keyGetter.invoke(o), (N) valueGetter.invoke(o));
            }
        } catch (Exception e) {
            log.error("makeEntityMap error list is " + collection, e);
            return map;
        }
        return map;
    }

    /**
     * 将list中的元素放到Map<String, V>以建立 key - value 索引——联合索引
     *
     * @param list          List<V> 元素列表
     * @param splitVar      分隔符
     * @param keyFieldNames String 元素的属性名称动态数组, 依次循环该属性的值作为索引key
     * @param <V>           value类型
     * @return Map<String       ,               V> key - value 索引
     */

    @SuppressWarnings("unchecked")
    public static <V> Map<String, V> makeEntityMapByKeys(List<V> list, String splitVar, String... keyFieldNames) {
        Map<String, V> map = new HashMap<>();
        if (list == null || list.size() == 0 || keyFieldNames == null || keyFieldNames.length == 0 || StringUtil.isEmpty(splitVar)) {
            return map;
        }
        try {
            List<Method> getterList = new ArrayList<>();
            for (String key : keyFieldNames) {
                getterList.add(getMethod(list.get(0).getClass(), key, "get"));
            }
            for (V item : list) {
                StringBuffer keys = new StringBuffer("");
                for (int i = 0; i < getterList.size(); i++) {
                    keys.append(getterList.get(i).invoke(item));
                    if (i < getterList.size() - 1) {
                        keys.append(splitVar);
                    }
                }
                map.put(keys.toString(), item);
            }
        } catch (Exception e) {
            log.error("makeEntityMap error list is " + list, e);
            return map;
        }
        return map;
    }
    /**
     * 获取getter或setter
     */
    @SuppressWarnings("unchecked")
    private static Method getMethod(@SuppressWarnings("rawtypes") Class clazz, String fieldName,
                                    String methodPrefix) throws NoSuchMethodException {
        String first = fieldName.substring(0, 1);
        String getterName = methodPrefix + fieldName.replaceFirst(first, first.toUpperCase());
        return clazz.getMethod(getterName);
    }

    public static void main(String[] args) {
        // List<Person> personList = Lists.newArrayList();
        // Person p1 = new Person();
        // p1.setId("1");
        // p1.setName("张三");
        // personList.add(p1);
        //
        // Person p2 = new Person();
        // p2.setId("2");
        // p2.setName("李四");
        // personList.add(p2);
        //
        // Person p3 = new Person();
        // p3.setId("2");
        // p3.setName("王五");
        // personList.add(p3);
        //
        // Map<String, Person> map1 = EntityUtil.makeEntityMap(personList, "id");
        // Map<String, Person> map2 = EntityUtil.makeEntityMapByKeys(personList, "-", "id", "name");
        //
        // System.out.println(11);
    }
}
