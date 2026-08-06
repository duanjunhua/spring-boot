package com.kingroad.pulsar.authorization.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-06 周四 13:51
 * @Version: v1.0
 * @Description: 自定义请求Filter
 */
@Slf4j
@Component
public class ApplicationRequestFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("Personal Filter, Filter Url: {}", request.getRequestURI());

        // TODO：进行请求相关预处理，如日志存储等


        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //
    }
}