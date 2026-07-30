package com.kingroad.pulsar.authorization.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:06
 * @Version: v1.0
 * @Description: 自定义Filter，可实现记录调用日志、排除XSS威胁字符、执行验证权限等
 */
@Slf4j
@Configuration
public class WebConfiguration {

    @Bean
    public RemoteIpFilter remoteIpFilter(){
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestLogFilter());

        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("PersonalFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 自定义请求Filter
     */
    public class RequestLogFilter implements Filter {


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


}
