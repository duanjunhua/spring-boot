package com.duanjh.module.interface_interact.interceptor;

import com.duanjh.module.interface_interact.aop.ResResult;
import com.duanjh.module.interface_interact.res.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: Michale J H Duan[JunHua]
 * @Date: 2024-07-16 星期二 16:13
 * @Version: v1.0
 * @Description: 重写返回体
 */
@Slf4j
@ControllerAdvice
public class ResResultHandler implements ResponseBodyAdvice<Object> {

    /**
     * 判断请求是否包含自定义注解，没有则直接返回
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        // 判断是否包含自定义返回注解
        ResResult resResult = (ResResult) request.getAttribute(ResResultInterceptor.RESPONSE_RESULT_ANN);

        return ObjectUtils.isEmpty(resResult) ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        log.debug("before body write, Customize return result handle...");

        return ResponseResult.success(body);
    }
}
