package com.duanjh.module.interface_interact.aop;

import java.lang.annotation.*;

/**
 * @Author: Michale J H Duan[JunHua]
 * @Date: 2024-07-16 星期二 16:01
 * @Version: v1.0
 * @Description: 自定义响应注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResResult {

}
