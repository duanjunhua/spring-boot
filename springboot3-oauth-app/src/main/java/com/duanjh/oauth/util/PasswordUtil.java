package com.duanjh.oauth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 18:03
 * @Version: v1.0
 * @Description:
 */
public class PasswordUtil {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
    }

}
