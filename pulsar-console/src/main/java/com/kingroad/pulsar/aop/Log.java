package com.kingroad.pulsar.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:17
 * @Version: v1.0
 * @Description: 自定义日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 操作
     */
    OperationType operation() default OperationType.UNKNOWN;

    /**
     * 操作描述
     */
    String description() default "";

    /**
     * 操作类型
     */
    public enum OperationType{
        ADD,
        UPDATE,
        DELETE,
        QUERY,
        PAGE,
        UNKNOWN
    }
}
