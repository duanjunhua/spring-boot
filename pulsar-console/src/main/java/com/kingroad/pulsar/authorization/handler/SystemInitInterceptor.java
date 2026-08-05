package com.kingroad.pulsar.authorization.handler;

import com.kingroad.pulsar.authorization.service.InitService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-04 周二 11:26
 * @Version: v1.0
 * @Description: 未初始化强制跳转引导页
 */
@Component
public class SystemInitInterceptor implements HandlerInterceptor {

    @Resource
    InitService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        // 放行初始化页面、初始化提交接口
        if(uri.startsWith("/system/init")){
            return true;
        }
        // 系统未初始化，强制跳转初始化页面
        if(!service.isInited()){
            response.sendRedirect("/system/init");
            return false;
        }
        return true;
    }
}
