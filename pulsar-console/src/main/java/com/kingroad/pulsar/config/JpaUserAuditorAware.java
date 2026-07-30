package com.kingroad.pulsar.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:01
 * @Version: v1.0
 * @Description: JPA中@CreateBy、@LastModifiedBy自动注入
 */
@Slf4j
@Component(value = "userAuditorAware")
public class JpaUserAuditorAware implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {

        //TODO：从上下文或认证对象中获取用户信息

        return Optional.empty();
    }
}
