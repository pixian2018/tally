package com.ztg.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/4/13 11:40
 */
@Data
public class A implements Serializable {
    private Integer age;
    private String name;
    B b;
}
