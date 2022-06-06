package com.boot.springboot.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-02
 * @Version: V1.0
 * @Description: 切面配置
 */
//@Order(1)     //定义切面的优先级，值越小，优先级越高
@Slf4j
@Aspect
@Component
public class WebAspect {

    /**
     * 实现AOP的切面主要有以下几个要素
     * 1. 使用@Aspect注解将一个java类定义为切面类
     * 2. 使用@Pointcut定义一个切入点，可以是一个规则表达式，也可以是一个注解等
     * 3. 根据需要在切入点不同位置的切入内容
     *  1）. 使用@Before在切入点开始处切入内容
     *  2）. 使用@After在切入点结尾处切入内容
     *  3）. 使用@AfterReturning在切入点return内容之后切入内容（可以用来处理返回值做一些加工处理）
     *  4）. 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
     *  5）. 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
     */

    //线程安全开始时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.boot.springboot.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{

        //设置开始时间
        startTime.set(System.currentTimeMillis());

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //记录下请求内容
        log.info("URL: {}", request.getRequestURL().toString());
        log.info("HTTP_METHOD: {}", request.getMethod());
        log.info("Remote IP: {}, Host: {}, Local Ip: {}", request.getRemoteAddr(), request.getRemoteHost(), request.getLocalAddr());
        log.info("CLASS_METHOD: {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "obj", pointcut = "webLog()")
    public void doAfterReturing(Object obj) throws Throwable{
        //处理完请求，返回内容
        log.info("RESPONSE: {}", obj);

        log.info("请求耗时： {} ms", System.currentTimeMillis() - startTime.get());
    }
}
