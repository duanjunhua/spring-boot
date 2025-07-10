package com.duanjh.oauth2.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-08 周二 13:57
 * @Version: v1.0
 * @Description:
 */
public class TestUtils {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
