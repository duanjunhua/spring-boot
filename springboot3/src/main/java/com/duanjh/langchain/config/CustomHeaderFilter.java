package com.duanjh.langchain.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-08 周五 16:37
 * @Version: v1.0
 * @Description: 防止Flux返回乱码
 */
@Configuration
public class CustomHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/event-stream;charset=UTF-8");
    }
}
