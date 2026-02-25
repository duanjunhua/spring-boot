package com.duanjh.redis.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 11:01
 * @Version: v1.0
 * @Description:
 */
@Configuration
// 设置Session失效时间，使用Redis Session之后，原SpringBoot的server.session.timeout属性不再生效
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class SessionConfig {
}
