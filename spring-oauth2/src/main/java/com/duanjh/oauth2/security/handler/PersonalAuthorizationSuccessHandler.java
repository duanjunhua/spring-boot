package com.duanjh.oauth2.security.handler;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-09 周三 09:22
 * @Version: v1.0
 * @Description: 自定义处理成功处理器（例如：在前后端分离的项目通常是使用Ajax请求完成认证，需要返回一个JSON结果告知前端认证结果，然后前端自行跳转页面）
 */
//@Configuration
public class PersonalAuthorizationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        data.put("message", "登陆成功");
        data.put("data", authentication);
        response.getWriter().println(JSON.toJSON(data));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
