package com.duanjh.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-06-16 周二 14:54
 * @Version: v1.0
 * @Description: 自定义也业务日志注解，也可自定义动态数据源注解，如DS，通过注解形式指定切换的master或slave数据源
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusLog {

    /**
     * 日志描述信息
     */
    String descption() default "";

}
