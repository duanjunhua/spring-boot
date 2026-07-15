package com.kingroad.pulsar.auth.handler;

import com.kingroad.pulsar.service.uo.SysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:34
 * @Version: v1.0
 * @Description: 登录成功更新登录时间
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private SysUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();

        userService.updateLastLogin(username);

        response.sendRedirect("/welcome");
    }
}
