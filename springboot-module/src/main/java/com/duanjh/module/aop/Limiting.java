package com.duanjh.module.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-07-03 周三
 * @Version: v1.0
 * @Description: 自定义接口限流防刷注解
 */
@Target(ElementType.METHOD) //定义注解标注的位置：方法
@Retention(RetentionPolicy.RUNTIME) // 定义注解在运行时加载
public @interface Limiting {

    /**
     * 定义默认放入限流桶中的token数量，每秒的访问次数，默认设置为10次
     */
    double limtTimes() default 10;

    String name() default "";

}
