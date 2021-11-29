package com.ztg.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 是否删除
 * @author: zhoutg
 * @time: 2018/9/3 16:01
 */
public enum IsDeleteEnum {

    Y("Y", "是"),
    N("N", "否");

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private String name;

    IsDeleteEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static IsDeleteEnum getEnum(String key) {
        for (IsDeleteEnum item : IsDeleteEnum.values()) {
            if (item.key.equals(key)) {
                return item;
            }
        }
        return null;
    }

    public static String getName(String key) {
        IsDeleteEnum item = getEnum(key);
        return item != null ? item.name : null;
    }
}
