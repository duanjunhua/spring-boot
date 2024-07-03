package com.duanjh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @Author: Michael J H Duan[Junhua Duan]
 * @Date: 2023-05-15 17:09
 * @Version: V1.0
 * @Description: 令牌存储策略配置
 */
@Configuration
public class AccessTokenConfig {

    /**
     * 令牌的存储策略
     */
    @Bean
    TokenStore tokenStore(){
        //TODO: 一般使用数据库存储或着JWT
        return new InMemoryTokenStore();
    }
}
