package com.duanjh.oauth2.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-03 周四 17:22
 * @Version: v1.0
 * @Description: 通过定义LogoutSuccessHandler处理器实现可以在登出成功的时候做一些操作，如：redis踢出等
 */
@Slf4j
@Configuration
public class PersonalizationSuccessLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登出成功处理器...");
    }
}
