package com.kingroad.pulsar.util;

import com.kingroad.pulsar.authorization.sso.SysOidcUser;
import com.kingroad.pulsar.domain.entity.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 安全上下文工具类
 */
public class SecurityUtil {

    /**
     * 获取当前登录完整本地用户
     */
    public static SysUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof SysOidcUser oidcUser) {
            return oidcUser.getSysUser();
        }
        return new SysUser();
    }


    /**
     * 获取当前登录用户ID
     */
    public static Long getUserId() {
        SysUser user = getLoginUser();
        return user == null ? null : user.getId();
    }

    /**
     * 获取登录账号名
     */
    public static String getUsername() {
        SysUser user = getLoginUser();
        return user == null ? null : user.getUsername();
    }

    /**
     * 获取客户端IP
     */
    public static String getClientIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip != null && ip.contains(",")) {
                ip = ip.split(",")[0].trim();
            }
            return ip;
        }
        return "unknown";
    }
}
