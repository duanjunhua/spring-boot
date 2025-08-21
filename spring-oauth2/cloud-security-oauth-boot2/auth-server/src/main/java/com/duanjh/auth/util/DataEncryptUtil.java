package com.duanjh.auth.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-12 周二 17:13
 * @Version: v1.0
 * @Description:
 */
public class DataEncryptUtil {

    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("secret", BCrypt.gensalt()));
    }
}
