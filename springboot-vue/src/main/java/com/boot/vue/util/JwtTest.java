package com.boot.vue.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-08
 * @Version: V1.0
 * @Description: JJWT
 */
public class JwtTest {

    static final String SECRET_KEY = "duanjh";

    /**
     * 生成令牌
     */
    public static String createJwt(){
       long now = System.currentTimeMillis();
       //设置过期时间为10S
        long exp = now + 10*1000;
        JwtBuilder builder = Jwts.builder().setId("jdyh")
                .setSubject("admin")    //主题：如用户名
                .setIssuedAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))    //签发时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)      //签名密钥
                .setExpiration(new Date(exp));  //设置过期时间
        return builder.compact();
    }

    /**
     * 解析令牌
     */
    public static Claims parserJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)  //签名密钥需一致
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public static void main(String[] args) throws Exception {
        String token = createJwt();
        System.out.println("生成的令牌: " + token);
        //Thread.sleep(10*1000);      //睡眠10秒会解析出错
        Claims claims = parserJwt(token);
        System.out.println("id: " + claims.getId());    //jdyh
        System.out.println("subject: " + claims.getSubject());  //admin
    }
}
