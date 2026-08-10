package com.kingroad.pulsar.authorization.handler;

import com.kingroad.pulsar.authorization.service.LocalUserDetailService;
import com.kingroad.pulsar.authorization.sso.SsoConst;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 10:26
 * @Version: v1.0
 * @Description: 自定义SSO登录，当用户未授权时，跳转到授权界面。若已授权直接进入系统
 */
@Slf4j
@Component
public class SsoLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    LocalUserDetailService uds;

    /**
     * 默认跳转
     */
    public SsoLoginSuccessHandler() {
        super.setDefaultTargetUrl("/index");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // 判断是否为SSO(OIDC)登录
        Object principal = authentication.getPrincipal();
        if (principal instanceof OidcUser oidcUser) {

            // 根据oidcUser中唯一标识（sub/邮箱/账号）查询本地用户
            String sub = oidcUser.getSubject();
            // 根据第三方唯一标识查询本地绑定用户
            UserDetails userDetails = uds.loadUserBySsoId(sub);

            // 用户不存在 OR 用户没有任何角色权限
            if (userDetails == null || userDetails.getAuthorities().isEmpty()) {
                // 重定向到角色申请页面
                getRedirectStrategy().sendRedirect(request, response, "/user/apply");
                return;
            }
        }

        // 正常拥有角色，执行原有跳转逻辑
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
