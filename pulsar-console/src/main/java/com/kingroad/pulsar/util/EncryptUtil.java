package com.kingroad.pulsar.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 14:59
 * @Version: v1.0
 * @Description:
 */
@Slf4j
public class EncryptUtil {

    public static final int RSA_KEY_SIZE = 2048;

    public enum ALG {
        SHA224("SHA-224"),
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512"),
        RSA("RSA");

        private String algorithm;

        ALG(String algorithm){
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
        return DigestUtils.md5Hex(str + salt);
    }

    /**
     * SHA加密：默认使用SHA-256
     * @param str 待加密字符
     * @param algorithm 加密算法：SHA-128、SHA-256、SHA-512，如：SHA.SHA128.name()
     * @return
     */
    public static String sha(String str, String algorithm){
        try {
            MessageDigest digest = MessageDigest.getInstance(ObjectUtil.isNull(algorithm)? ALG.SHA256.name() : algorithm);
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

    /*------------------------- RSA Util ---------------------------------*/

    /**
     * 生成密钥对
     */
    public static KeyPair getRsaKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALG.RSA.name());
            generator.initialize(RSA_KEY_SIZE);
            return generator.generateKeyPair();
        }catch (NoSuchAlgorithmException e) {
            log.error("密钥对生成失败！{}", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 公钥加密
     */
    public static String encryptWithRsa(String rawText, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALG.RSA.name());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(rawText.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        }catch (Exception e) {
            log.error("公钥加密失败！{}", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 私钥解密
     */
    public static String decryptWithRsa(String cipherText, PrivateKey privateKey) {
        try {
            byte[] bytes = Base64.getDecoder().decode(cipherText);
            Cipher cipher = Cipher.getInstance(ALG.RSA.name());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(bytes));
        }catch (Exception e) {
            log.error("解密失败失败！{}", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * base64字符串转公钥
     */
    public static PublicKey getRsaPublicKey(String base64PubKey) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64PubKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(ALG.RSA.name());
            return factory.generatePublic(spec);
        }catch (Exception e) {
            log.error("字符串转公钥失败！{}", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * base64裸串 → PrivateKey（PKCS8）
     */
    public static PrivateKey getRsaPrivateKey(String base64PriKey) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64PriKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(ALG.RSA.name());
            return factory.generatePrivate(spec);
        }catch (Exception e) {
            log.error("字符串转私钥失败！{}", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 包装公钥为PEM格式（jsencrypt强制要求）
     */
    public static String wrapPublicKey(String base64Pub) {
        return "-----BEGIN PUBLIC KEY-----\n"
                + base64Pub.replaceAll("(.{64})", "$1\n")
                + "\n-----END PUBLIC KEY-----";
    }

    public static void main(String[] args) {
        String raw = "123456";
        String salt = "pulsar-app-console";
        // 调用工具类
        String md5Val = EncryptUtil.md5(raw, salt);
        System.out.println(md5Val);
    }
}
