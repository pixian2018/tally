package com.ztg.demo1.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @Description: 数组工具类
 * @author: zhoutg
 * @time: 2018/8/6 16:22
 */
public class ArrayUtil {
    /**
     * 判断数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }

    /**
     * 判断数组是否不为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    /**
     * 数组合并
     *
     * @param array        一个数组
     * @param anotherArray 另外一个数组
     * @param <T>          数组类型
     * @return 合并后的数组
     */
    public static <T> T[] add(T[] array, T[] anotherArray) {
        return ArrayUtils.addAll(array, anotherArray);
    }
}
