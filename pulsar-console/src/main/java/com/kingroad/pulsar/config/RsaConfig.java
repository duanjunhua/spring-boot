package com.kingroad.pulsar.config;

import com.kingroad.pulsar.util.EncryptUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.util.Base64;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-05 周三 14:48
 * @Version: v1.0
 * @Description: 登录的全局 RSA 密钥，每次启动应用初始化
 */
@Slf4j
@Configuration
public class RsaConfig {

    public static String PUBLIC_KEY;
    public static String PRIVATE_KEY;

    @PostConstruct
    public void init() throws Exception {
        KeyPair keyPair = EncryptUtil.getRsaKeyPair();
        PUBLIC_KEY = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        PRIVATE_KEY = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        log.info(PUBLIC_KEY);
        log.info(PRIVATE_KEY);
    }
}
