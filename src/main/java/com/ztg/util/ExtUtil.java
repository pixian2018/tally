package com.ztg.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 扩展工具类
 * @author: zhoutg
 * @date: 2021/4/27 13:10
 */
public class ExtUtil {

    /**
     * 以Map<key, List<V> 形式返回
     *
     * @param list
     * @param property
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, List<V>> getKeyList(List<V> list, String property) {
        if (ListUtil.isEmpty(list)) {
            return new LinkedHashMap<>();
        }
        return list.stream().collect(
                Collectors.groupingBy(r -> ReflectUtil.getProperty(r, property), LinkedHashMap::new, Collectors.toList()));
    }

    /**
     * 以Map<key, V> 形式返回，如果key相同，会覆盖前面的内容
     *
     * @param list
     * @param property
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> getKeyObject(List<V> list, String property) {
        if (ListUtil.isEmpty(list)) {
            return new LinkedHashMap<>();
        }
        return list.stream().collect(
                Collectors.toMap(k -> ReflectUtil.getProperty(k, property), v -> v, (v1, v2) -> (v2), LinkedHashMap::new));
    }

    /**
     * 以Map<key, V> 形式返回，如果key相同，会覆盖前面的内容
     *
     * @param list          列表
     * @param splitSmybool  key分隔符
     * @param multiProperty 多个属性
     * @param <V>
     * @return
     */
    public static <V> Map<String, V> getMultiKeyObject(List<V> list, String splitSmybool, String... multiProperty) {
        if (ListUtil.isEmpty(list)) {
            return new LinkedHashMap<>();
        }
        return list.stream().collect(Collectors.toMap(k -> {
            List<String> keyList = Lists.newArrayList();
            for (String property : multiProperty) {
                keyList.add(ReflectUtil.getProperty(k, property));
            }
            return StringUtils.join(keyList, splitSmybool);
        }, v -> v, (v1, v2) -> (v2), LinkedHashMap::new));
    }

    /**
     * 以Map<key, List<V>> 形式返回
     *
     * @param list          列表
     * @param splitSmybool  key分隔符
     * @param multiProperty 多个属性
     * @param <V>
     * @return
     */
    public static <V> Map<String, List<V>> getMultiKeyList(List<V> list, String splitSmybool, String... multiProperty) {
        if (ListUtil.isEmpty(list)) {
            return new LinkedHashMap<>();
        }
        return list.stream().collect(Collectors.groupingBy(k -> {
            List<String> keyList = Lists.newArrayList();
            for (String property : multiProperty) {
                keyList.add(ReflectUtil.getProperty(k, property));
            }
            return StringUtils.join(keyList, splitSmybool);
        }, LinkedHashMap::new, Collectors.toList()));
    }

    /**
     * 以Map<K, V> 形式返回
     *
     * @param list
     * @param keyProperty
     * @param valueProperty
     * @param <K>
     * @param <R>
     * @param <V>
     * @return
     */
    public static <K, R, V> Map<K, R> getKeyValue(List<V> list, String keyProperty, String valueProperty) {
        if (ListUtil.isEmpty(list)) {
            return new LinkedHashMap<>();
        }
        return list.stream().collect(Collectors.toMap(k -> ReflectUtil.getProperty(k, keyProperty),
                v -> ReflectUtil.getProperty(v, valueProperty), (v1, v2) -> (v2), LinkedHashMap::new));
    }

    /**
     * 以Map形式返回所有的key，值固定
     *
     * @param list
     * @param keyProperty
     * @param r
     * @param <K>
     * @param <R>
     * @param <V>
     * @return
     */
    public static <K, R, V> Map<K, R> getKeyMap(List<V> list, String keyProperty, R r) {
        if (ListUtil.isEmpty(list)) {
            return new LinkedHashMap<>();
        }
        return list.stream().collect(Collectors.toMap(k -> ReflectUtil.getProperty(k, keyProperty),
                v -> r, (v1, v2) -> (v2), LinkedHashMap::new));
    }

    /**
     * 去除重复元素——主要用于字符串
     *
     * @param list
     */
    public static <T> List<T> removeRepeat(List<T> list) {
        if (ListUtil.isNotEmpty(list) && list.size() > 1) {
            list = list.stream().distinct().collect(Collectors.toList());
        }
        return list;
    }

    /**
     * 根据属性去除重复元素——主要用于对象
     *
     * @param list
     */
    public static <T> void removeRepeat(List<T> list, String field) {
        if (ListUtil.isEmpty(list) || list.size() < 2) {
            return;
        }
        // value可以用来过滤字符串列表
        if (StringUtil.isBlank(field)) {
            field = "value";
        }
        List<Object> filterObj = Lists.newArrayList();// 过滤集
        Iterator<T> iterator = list.iterator();
        T t = null;
        Object obj = null;
        while (iterator.hasNext()) {
            t = iterator.next();
            obj = ReflectUtil.getProperty(t, field);
            if (obj != null) {
                if (filterObj.contains(obj)) {
                    iterator.remove();
                } else {
                    filterObj.add(obj);
                }
            }
        }
    }

    /**
     * 在源列表中移除过滤的内容
     *
     * @param orginList
     * @param filterList
     * @param field
     * @param <T>
     * @param <V>
     */
    public static <T, V> void removeFilter(List<T> orginList, List<V> filterList, String field) {
        if (ListUtil.isEmpty(orginList) || ListUtil.isEmpty(filterList)) {
            return;
        }
        if (StringUtil.isBlank(field)) {
            field = "value";
        }
        Object obj = null;
        Iterator<T> itOrgin = orginList.iterator();
        final String finalField = field; // field 可能会重新赋值为value，需要使用final修饰
        List<Object> filterObj = filterList.stream().map(r -> ReflectUtil.getProperty(r, finalField))
                .filter(Objects::nonNull).collect(Collectors.toList());
        while (itOrgin.hasNext()) {
            obj = ReflectUtil.getProperty(itOrgin.next(), finalField);
            if (obj != null) {
                if (filterObj.contains(obj)) {
                    itOrgin.remove();
                } else {
                    filterObj.add(obj);
                }
            }
        }
    }

    /**
     * 将字符串转成List<Long>
     *
     * @param s
     * @param split 分隔符，例如：“，”
     * @return
     */
    public static List<Long> convLongList(String s, String split) {
        if (StringUtil.isBlank(s)) {
            return Lists.newArrayList();
        }
        return Arrays.stream(s.split(split)).map(r -> Long.valueOf(r)).collect(Collectors.toList());
    }

    /**
     * 将字符串转成List<Integer>
     *
     * @param s
     * @param splitSymbol 分隔符
     * @return
     */
    public static List<Integer> convIntegerList(String s, String splitSymbol) {
        if (StringUtil.isBlank(s)) {
            return Lists.newArrayList();
        }
        return Arrays.stream(s.split(splitSymbol)).map(r -> Integer.valueOf(r)).collect(Collectors.toList());
    }

    public static void main(String[] args) {


    }
}
