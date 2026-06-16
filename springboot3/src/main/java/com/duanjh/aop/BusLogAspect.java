package com.duanjh.aop;

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
 * @Date: 2026-06-16 周二 14:56
 * @Version: v1.0
 * @Description: 自定定义界面
 */
@Slf4j
@Aspect
@Component
public class BusLogAspect {

    /**
     * 指定自定义注解
     */
    @Pointcut("@annotation(com.duanjh.aop.BusLog)")
    public void busLogAspect(){

    }

    @Around("busLogAspect()")       // 指定自定义注解方法
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        /**
         * 注解标注的方法
         */
        Method method = signature.getMethod();

        BusLog busLogAnnotation = method.getAnnotation(BusLog.class);

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String methodDesc = StringUtils.isBlank(busLogAnnotation.descption()) ? "未指定方法描述" : busLogAnnotation.descption();

        log.info("【BusLog Annotation】Start: {}:{}  {}", className, methodName, methodDesc);
        Object result = joinPoint.proceed();

        /**
         * 此处可以做相关的操作，如存储数据库保留业务日志等
         */

        return result;
    }
}
