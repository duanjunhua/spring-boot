package com.duanjh.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 16:08
 * @Version: v1.0
 * @Description: Redis共享Session配置，实现跨子系统SSO免重复登录
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400) // session有效期1天
public class RedisSessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SSO_SESSION_ID");
        serializer.setCookiePath("/");
        // 多系统同二级域名时放开：如auth.xxx.com、system1.xxx.com
        // serializer.setDomainNamePattern("^.+?\\.(\\w+\\.\\w+)$");
        return serializer;
    }
}
