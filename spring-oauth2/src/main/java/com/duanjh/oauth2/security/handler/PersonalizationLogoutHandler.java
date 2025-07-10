package com.duanjh.oauth2.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-03 周四 17:22
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Configuration
public class PersonalizationLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("登出处理器");
        try {
            // 登出成功，响应结果给客户端，通常是一个JSON数据
            response.getWriter().println("Logout Successful.");
        }catch (Exception e){
            log.error("登出处理异常：{}", e.getLocalizedMessage());
        }
    }
}
