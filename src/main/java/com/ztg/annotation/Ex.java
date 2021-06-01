package com.ztg.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: excel注解
 * @author: zhoutg
 * @time: 2021/5/21 13:49
 */
@Target(value = { ElementType.TYPE, ElementType.FIELD })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Ex {
    public String name();
}
