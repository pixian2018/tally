package com.ztg.util;

import java.util.List;

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

    public static void main(String[] args) {

    }
}
