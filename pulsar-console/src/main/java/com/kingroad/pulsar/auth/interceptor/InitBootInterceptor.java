package com.kingroad.pulsar.auth.interceptor;

import com.kingroad.pulsar.constant.CommonConst;
import com.kingroad.pulsar.service.config.SysConfigService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:37
 * @Version: v1.0
 * @Description: 初始化强制跳引导页
 */
@Component
public class InitBootInterceptor implements HandlerInterceptor {

    @Resource
    private SysConfigService configService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String initFlag = configService.getConfigValue("init_admin_flag");

        if(!CommonConst.FLAG_ONE.equals(initFlag)) {

            String uri = request.getRequestURI();

            // 跳转到引导界面
            if(!uri.startsWith("/init") && !uri.startsWith("/static")) {
                response.sendRedirect("/init/guide");
                return false;
            }
        }
        return true;
    }
}
