package com.ztg.enums;


import lombok.Setter;

/**
 * @author zhoutg
 * @Description:
 * @date 2018年11月21日 下午2:31:42
 */
public enum StatusEnum implements KeyedNamed {
    Disable(0, "禁用"),
    Enable(1, "启用");

    @Setter
    private int key;

    @Setter
    private String name;

    StatusEnum(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public static StatusEnum getEnum(int key) {
        for (StatusEnum item : StatusEnum.values()) {
            if (item.key == key) {
                return item;
            }
        }
        return null;
    }

    public static String getName(int key) {
        StatusEnum item = getEnum(key);
        return item != null ? item.name : null;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }
}

