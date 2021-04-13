package com.ztg.demo1.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/4/13 11:40
 */
@Data
public class B implements Serializable {
    private Integer age;
    private String name;
    private List<C> cList;
}
