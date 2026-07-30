package com.kingroad.pulsar.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:22
 * @Version: v1.0
 * @Description: 自定义日志注解处理器
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 指向自定义日志注解
     */
    @Pointcut("@annotation(com.kingroad.pulsar.aop.Log)")
    public void logAspect(){

    }

    /**
     * 自定义注解处理
     */
    @Around("logAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        /**
         * 注解标注的方法
         */
        Method method = signature.getMethod();

        Log logs = method.getAnnotation(Log.class);

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String methodDesc = StringUtils.isBlank(logs.description()) ? "未指定方法描述" : logs.description();

        log.info("【自定义注解捕获日志】Start: {}:{}  {} --- {}", className, methodName, methodDesc, logs.operation().name());
        Object result = joinPoint.proceed();

        /**
         * 此处可以做相关的操作，如存储数据库保留业务日志等
         */

        return result;
    }
}
