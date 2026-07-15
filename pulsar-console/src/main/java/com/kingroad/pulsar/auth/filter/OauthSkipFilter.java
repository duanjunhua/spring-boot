package com.kingroad.pulsar.auth.filter;

import cn.hutool.core.util.ObjectUtil;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.service.uo.SysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:32
 * @Version: v1.0
 * @Description: 超管跳过 OAuth 过滤器
 */
@Component
public class OauthSkipFilter extends OncePerRequestFilter {

    @Resource
    private SysUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()) {
            String username = auth.getName();

            SysUser user = userService.getByUsername(username);

            // 超级管理员直接放行，跳过OAuth强制校验
            if(ObjectUtil.isNotNull(user) && user.getIsSuperAdmin()) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 普通用户执行原有OAuth校验逻辑
        filterChain.doFilter(request, response);
    }
}
