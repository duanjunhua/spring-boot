package com.duanjh.oauth2.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-08 周二 13:57
 * @Version: v1.0
 * @Description:
 */
public class TestUtils {

    public static void main(String[] args) {
        //PasswordEncoder encoder = new BCryptPasswordEncoder();
        //System.out.println(encoder.encode("123456"));
        System.out.println(generateJwtToken("{\"username\":\"duanjh\",\"password\":\"123456\"}"));

    }

    public static String generateJwtToken(String subject) {
        //系统时间
        long now = System.currentTimeMillis();

        //计算过期时间30秒过期
        long exp = now+1000*30;

        //使用Jwts签发令牌
        JwtBuilder jwtBuilder = Jwts.builder()
                //设置唯一编号
                .setId( "duanjh" )
                //主题，可以扔一个JSON
                .setSubject(subject)
                //签发时间
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(exp))
                //扩展数据，角色
                .claim("roles","admin")
                //使用HS256算法，并设置SecretKey(字符串)，SecretKey长度必须大于等于4
                .signWith(SignatureAlgorithm.HS256, "duanjh_password" );

        return jwtBuilder.compact();
    }
}
