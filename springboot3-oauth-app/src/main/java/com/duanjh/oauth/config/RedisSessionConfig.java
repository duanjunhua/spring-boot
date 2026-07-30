package com.duanjh.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 17:07
 * @Version: v1.0
 * @Description:
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        // 使用JSON序列化，无需实现Serializable
        return new JdkSerializationRedisSerializer();
    }

}
