package com.ztg.util;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: List 工具类
 * @author: zhoutg
 * @date: 2017/12/28 15:36
 * @version: V1.0
 */
public class ListUtil {

    /**
     * 判断List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (null == list) {
            return Boolean.TRUE;
        }
        if (list.isEmpty()) {
            return Boolean.TRUE;
        }
        if (list.size() < 1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断List是否为非空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    /**
     * 添加列表
     *
     * @param source
     * @param addList
     * @param <T>
     */
    public static <T> void addList(List<T> source, List<? extends T> addList) {
        if (source == null) {
            return;
        }
        if (ListUtil.isNotEmpty(addList)) {
            source.addAll(addList);
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
     * @param split 分隔符
     * @return
     */
    public static List<Integer> convIntegerList(String s, String split) {
        if (StringUtil.isBlank(s)) {
            return Lists.newArrayList();
        }
        return Arrays.stream(s.split(split)).map(r -> Integer.valueOf(r)).collect(Collectors.toList());
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

    public static void main(String[] args) {

    }
}
