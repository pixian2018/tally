package com.ztg.util;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @description: 反射工具类
 * @author: zhoutg
 * @date: 2021/4/27 13:29
 */
public class ReflectUtil {

    /**
     * 获取属性对应的值，以list形式返回
     *
     * @param list
     * @param propertyName
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> List<V> getPropertyList(List<T> list, String propertyName) {
        List<V> res = Lists.newArrayList();
        if (ListUtil.isEmpty(list)) {
            return res;
        }
        for (T t : list) {
            try {
                V val = getProperty(t, propertyName);
                if (val != null) {
                    // 字符串类型不为空判断
                    if (StringUtil.isNotBlank(String.valueOf(val))) {
                        res.add(val);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 获取属性对应的值，以list形式返回——重载
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<String> getPropertyList(List<T> list) {
        return getPropertyList(list, "name");
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 获取对象的属性值,直接使用getDeclaredFields()方法只能获取当前类声明的字段，需要递归向上获取
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */
    public static <T> T getProperty(Object object, String fieldName) {
        try {
            //根据 对象和属性名通过反射获取Field对象
            Field field = getDeclaredField(object, fieldName);
            // 容错处理
            if (field == null) {
                return null;
            }
            //抑制Java对其的检查
            field.setAccessible(true);
            //获取 object 中 field 所代表的属性值
            return (T) field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象赋值
     *
     * @param object
     * @param property
     * @param value
     */
    public static void setProperty(Object object, String property, Object value){
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, property) ;
        //抑制Java对其的检查
        field.setAccessible(true) ;
        try {
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据指定字段按照字符串排序
     *
     * @param tList
     * @param property
     * @param <T>
     */
    public <T> void sort(List<T> tList, String property) {
        if (ListUtil.isNotEmpty(tList) && tList.size() > 1) {
            tList.sort((obj1, obj2) -> {
                String v1 = getProperty(obj1, property);
                String v2 = getProperty(obj2, property);
                if (StringUtil.isBlank(v1)) {
                    return -1;
                }
                if (StringUtil.isBlank(v2)) {
                    return 1;
                }
                return v1.compareTo(v2);
            });
        }
    }

    public static void main(String[] args) {

    }
}
