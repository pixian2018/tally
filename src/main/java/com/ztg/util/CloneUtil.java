package com.ztg.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @description: 对象克隆
 * 一般有两种方法：
 * 1：对象实现Cloneable接口，重写Object中的clone方法(浅克隆，共享引用变量)
 * 2：对象实现Serializable，使用序列化和反序列化配合内存流的方式实现克隆(深度克隆)
 * @author: zhoutg
 * @time: 2021/6/2 9:21
 */
public class CloneUtil {

    /**
     * 对象克隆
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T extends Serializable> T clone(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            T t = (T) objectInputStream.readObject();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
