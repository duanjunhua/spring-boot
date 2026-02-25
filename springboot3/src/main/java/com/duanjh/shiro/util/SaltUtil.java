package com.duanjh.shiro.util;


import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Random;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-12 周四 15:11
 * @Version: v1.0
 * @Description: 随即盐工具
 */
public class SaltUtil {

    public static final String STRS = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz(#$^@%&*!)";

    public static String generateRandomSalt(int length) {

        char[] chars = STRS.toCharArray();

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(chars.length);
            char ch = chars[number];
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        SimpleHash md5Hash = new SimpleHash("MD5", "vip", "vip8d78869f470951332959580424d4bf4f", 1024);

        System.out.println(md5Hash.toHex());
    }

}
