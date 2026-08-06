package com.kingroad.pulsar.authorization;

import com.kingroad.pulsar.common.CommonConst;
import com.kingroad.pulsar.config.RsaConfig;
import com.kingroad.pulsar.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
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

    /**
     * 密码盐
     */
    public static final String SALT = "pulsar-app-console";

    @Override
    public String encode(CharSequence password) {
        return EncryptUtil.md5(password.toString(), SALT);
    }

    @Override
    public boolean matches(CharSequence password, String encodedPassword) {

        log.info("进入前端密码解码与密码校验");


        String rawPassword = EncryptUtil.decryptWithRsa(String.valueOf(password), EncryptUtil.getRsaPrivateKey(RsaConfig.PRIVATE_KEY));
        if(StringUtils.isBlank(rawPassword)){
            throw new BadCredentialsException("密码解密失败，请重试");
        }
        return encodedPassword.equals(encode(rawPassword));
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        // MD5是固定算法
        return  false;
    }
}
