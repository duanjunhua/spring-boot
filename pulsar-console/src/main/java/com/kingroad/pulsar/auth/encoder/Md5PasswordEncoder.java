package com.kingroad.pulsar.auth.encoder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 15:36
 * @Version: v1.0
 * @Description: 自定义MD5 PasswordEncoder
 */
@Slf4j
@Component
public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence password) {
        return DigestUtils.md5Hex(password.toString());
    }

    @Override
    public boolean matches(CharSequence password, String encodedPassword) {
        return encodedPassword.equals(DigestUtils.md5Hex(password.toString()));
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        // MD5是固定算法
        return  false;
    }
}
