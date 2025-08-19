package com.duanjh.annotation;

import com.duanjh.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-19 周二 09:55
 * @Version: v1.0
 * @Description: 动态数据源注解切面
 */
@Slf4j
@Aspect
@Component
public class DSAspect {

    @Pointcut("@annotation(com.duanjh.annotation.DS)")
    public void dynamicDataSource(){

    }

    @Around("dynamicDataSource()")
    public Object datasourceAround(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();

        Method method = signature.getMethod();

        DS ds = method.getAnnotation(DS.class);

        if (Objects.nonNull(ds)){
            DataSourceContextHolder.setDataSource(ds.value());
        }

        try {
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }

}
