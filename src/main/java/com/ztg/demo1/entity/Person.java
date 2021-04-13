package com.ztg.demo1.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/4/13 11:40
 */
@Data
public class Person implements Serializable {
    private String id;
    private Integer age;
    private String name;
}
