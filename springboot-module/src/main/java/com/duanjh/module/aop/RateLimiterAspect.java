package com.duanjh.module.aop;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-07-03 周三
 * @Version: v1.0
 * @Description: 自定义接口限流防刷切面
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {

    private ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();
    private RateLimiter limiter;

    @Pointcut("@annotation(com.duanjh.module.aop.Limiting)")
    public void apiLimite(){

    }

    @Around("apiLimite()")
    public Object limitAround(ProceedingJoinPoint point) throws Throwable{
        // 获取拦截的方法
        Signature signature = point.getSignature();

        // 获取拦截的方法名
        MethodSignature methodSignature = (MethodSignature) signature;

        // 返回被织入增加处理目标对象
        Object target = point.getTarget();

        // 获取方法
        Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        // 获取注解信息
        Limiting annotation = method.getAnnotation(Limiting.class);
        double limitTimes = annotation.limtTimes();
        String functionName = method.getName();

        if(limiters.containsKey(functionName)){
            limiter = limiters.get(functionName);
        } else {
            limiters.put(functionName, RateLimiter.create(limitTimes));
            limiter = limiters.get(functionName);
        }

        if(limiter.tryAcquire()){
            log.info("The api access limit process completed!");
            return point.proceed();
        }else {
            throw new RuntimeException("Too many requests, Please try again later!");
        }
    }
}
