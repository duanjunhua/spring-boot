package com.duanjh.dynamicds;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-12 周四 14:51
 * @Version: v1.0
 * @Description: AOP自动选择数据源
 */
@Slf4j
@Aspect
@Component
public class DynamicDsAspect {

    @Autowired
    private DynamicDs dynamicDs;

    /**
     * 定义切面
     */
    @Pointcut("execution(* org.springframework.data.repository.CrudRepository.*(..))||execution(* com.duanjh.dynamicds.repository.*.*(..))")
    private void aspect(){

    }

    /**
     * 打印数据源引入
     */
    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        if (method.startsWith("find") || method.startsWith("select") || method.startsWith("query") || method.startsWith("search")) {
            DynamicDs.setDataSource(DsType.SECONDARY);
        } else {
            DynamicDs.setDataSource(DsType.PRIMARY);
            log.info("Switch to Primary datasource...");
        }
        log.info("aop当前使用的数据源是:{}", dynamicDs.getConnection().getCatalog());
        try {
            return joinPoint.proceed();
        } finally {
            log.info("清除 datasource router...");
            dynamicDs.clearDataSource();
        }
    }
}
