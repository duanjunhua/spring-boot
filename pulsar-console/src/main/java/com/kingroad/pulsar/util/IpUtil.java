package com.kingroad.pulsar.util;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 13:55
 * @Version: v1.0
 * @Description:
 */
public class IpUtil {

    /** 获取客户端真实IP */
    public static String getIpAddr(HttpServletRequest request) {
        String xForwarded = request.getHeader("X-Forwarded-For");
        if (StrUtil.isNotBlank(xForwarded) && !"unknown".equalsIgnoreCase(xForwarded)) {
            return xForwarded.split(",")[0];
        }
        String proxyIp = request.getHeader("Proxy-Client-IP");
        if (StrUtil.isNotBlank(proxyIp) && !"unknown".equalsIgnoreCase(proxyIp)) {
            return proxyIp;
        }
        return request.getRemoteAddr();
    }

    /** 获取客户端真实IP */
    public static InetAddress getInetAddr(HttpServletRequest request) {
        try {
            String xForwarded = request.getHeader("X-Forwarded-For");
            if (StrUtil.isNotBlank(xForwarded) && !"unknown".equalsIgnoreCase(xForwarded)) {
                return  InetAddress.getByName( xForwarded.split(",")[0]);
            }
            String proxyIp = request.getHeader("Proxy-Client-IP");
            if (StrUtil.isNotBlank(proxyIp) && !"unknown".equalsIgnoreCase(proxyIp)) {
                return InetAddress.getByName( proxyIp);
            }
            return InetAddress.getByName(request.getRemoteAddr());
        }catch (UnknownHostException e) {
            System.out.println("转换失败");
        }
        return null;
    }

    public static InetAddress convertIpToInet(String ip) {
        try {
            return InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            System.out.println("转换失败");
        }
        return null;
    }
}
