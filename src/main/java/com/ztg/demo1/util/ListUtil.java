package com.ztg.demo1.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: List 工具类
 * @author: gaodm
 * @date: 2017/12/28 15:36
 * @version: V1.0
 */
public class ListUtil {
    /**
     * list的第一行
     */
    public static final int FIRST = 0;

    private ListUtil() {

    }

    /**
     * 创建List对象
     *
     * @param <E> 泛型，
     * @return
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

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
}
