package com.duanjh.annotation;

import java.lang.annotation.*;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-19 周二 09:53
 * @Version: v1.0
 * @Description: 动态数据源切换的注解
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DS {

    String value() default "typt";

}
