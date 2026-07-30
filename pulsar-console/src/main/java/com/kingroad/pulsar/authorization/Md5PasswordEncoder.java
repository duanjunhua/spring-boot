package com.kingroad.pulsar.authorization;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 14:41
 * @Version: v1.0
 * @Description:
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
        log.info("密码：{}，加密密码：{}", DigestUtils.md5Hex(password.toString()), encodedPassword);
        return encodedPassword.equals(DigestUtils.md5Hex(password.toString()));
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        // MD5是固定算法
        return  false;
    }

}
