//package com.kingroad.pulsar.auth.handler;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.kingroad.pulsar.constant.OperateType;
//import com.kingroad.pulsar.entity.core.SysAuditLog;
//import com.kingroad.pulsar.entity.uo.SysUser;
//import com.kingroad.pulsar.service.core.SysAuditLogService;
//import com.kingroad.pulsar.service.uo.SysUserService;
//import com.kingroad.pulsar.util.IpUtil;
//import jakarta.annotation.Resource;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//
///**
// * @Author: Michael J H Duan[JunHua]
// * @Date: 2026-07-14 周二 10:34
// * @Version: v1.0
// * @Description: 登录成功更新登录时间
// */
//@Slf4j
//@Component
//public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Resource
//    private SysUserService userService;
//
//    @Resource
//    private SysAuditLogService auditLogService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//
//        // 兼容表单登录与OAuth登录两种认证对象
//        Object principal = authentication.getPrincipal();
//        SysUser loginUser;
//        if(principal instanceof OAuth2User oAuth2User){
//            String unionId = oAuth2User.getAttribute("sub");
//            loginUser = userService.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, unionId));
//        }else {
//            // 原有账号密码登录逻辑
//            loginUser = (SysUser) principal;
//        }
//
//        String username = loginUser.getUsername();
//
//        log.info("登录成功：{}", username);
//
//        SysAuditLog audit = new SysAuditLog();
//
//        audit.setOperatorId(username);
//        audit.setOperationType(OperateType.PC_LOGIN.description());
//        audit.setTargetResource("/ssoLogin");
//        audit.setSourceIp(IpUtil.getIpAddr(request));
//        audit.setCreateAt(LocalDateTime.now());
//
//        auditLogService.save(audit);
//
//        response.sendRedirect("/welcome");
//    }
//}
