package com.ztg.enums;

/**
 * @Description: 状态和名字基础接口
 * @author: gaodm
 * @time: 2018/9/3 16:13
 */
public interface KeyedNamed {
    /**
     * 状态值
     */
    int getKey();

    /**
     * 状态描述
     */
    String getName();
}
