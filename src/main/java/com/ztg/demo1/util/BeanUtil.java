package com.ztg.demo1.util;

import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 对象转换工具类
 * @author: gaodm
 * @time: 2018/12/14 14:21
 */
public class BeanUtil {
    /**
     * 把一个对象的属性值复制给另外一个对象的属性值
     *
     * @param source 源对象，被转换的对象
     * @param target 目标对象，即转换后对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 复制集合
     *
     * @param <E>
     * @param source           转换前的列表
     * @param destinationClass 转换后列表类
     * @return 转换后列表
     */
    public static <E> List<E> listCopyTo(List<?> source, Class<E> destinationClass) {
        try {
            if (source.size() == 0) {
                return Collections.emptyList();
            }
            List<E> res = new ArrayList<E>(source.size());
            for (Object o : source) {
                E e = destinationClass.newInstance();
                BeanUtils.copyProperties(o, e);
                res.add(e);
            }
            return res;
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
