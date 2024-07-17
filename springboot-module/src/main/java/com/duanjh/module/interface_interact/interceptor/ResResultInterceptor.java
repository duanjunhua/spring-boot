package com.duanjh.module.interface_interact.interceptor;

import com.duanjh.module.interface_interact.aop.ResResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * @Author: Michale J H Duan[JunHua]
 * @Date: 2024-07-16 星期二 16:03
 * @Version: v1.0
 * @Description: 自定义响应注解请求拦截器
 */
@Slf4j
@Configuration
public class ResResultInterceptor implements HandlerInterceptor {

    // 标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    /**
     * 获取此请求，判断是否需要返回值包装，设置一个属性标记
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){

            final HandlerMethod hm = (HandlerMethod)handler;

            final Class<?> clazz = hm.getBeanType();
            final Method method = hm.getMethod();

            // 判断是否在类对象上面有自定义注解
            if(clazz.isAnnotationPresent(ResResult.class)){
                log.debug("Class has specify annotion, handle [ResResult] to return");
                //类上有，设置当前请求返回体，封装往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANN, clazz.getAnnotation(ResResult.class));
                return true;
            }

            if(method.isAnnotationPresent(ResResult.class)){
                log.debug("Method has specify annotion, handle [ResResult] to return");
                //方法上有自定义注解
                request.setAttribute(RESPONSE_RESULT_ANN, method.getAnnotation(ResResult.class));
            }
        }
        return true;
    }
}
