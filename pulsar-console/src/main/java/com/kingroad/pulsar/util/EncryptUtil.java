package com.kingroad.pulsar.util;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 14:59
 * @Version: v1.0
 * @Description:
 */
public class EncryptUtil {

    public enum SHA{
        SHA224("SHA-224"),
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512");

        private String algorithm;

        SHA(String algorithm){
            this.algorithm = algorithm;
        }
    }

    /**
     * 使用MD5进行加密
     * @param str 原始字符
     */
    public static String md5(String str) {
        try {

            // 获取MD5摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            md.update(str.getBytes());

            // 获取密文
            byte[] digest = md.digest();

            // 将密文转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    public static String md5(String str, String salt) {
        return DigestUtils.md5Hex(str.toString());
    }

    /**
     * SHA加密：默认使用SHA-256
     * @param str 待加密字符
     * @param algorithm 加密算法：SHA-128、SHA-256、SHA-512，如：SHA.SHA128.name()
     * @return
     */
    public static String sha(String str, String algorithm){
        try {
            MessageDigest digest = MessageDigest.getInstance(ObjectUtil.isNull(algorithm)? SHA.SHA256.name() : algorithm);
            byte[] hash = digest.digest(str.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("算法" + algorithm + "不存在!");
        }
    }

    public static void main(String[] args) {
        System.out.println(sha("123456", SHA.SHA256.name()));
    }
}
