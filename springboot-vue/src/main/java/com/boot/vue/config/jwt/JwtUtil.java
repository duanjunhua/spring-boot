package com.boot.vue.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-08
 * @Version: V1.0
 * @Description: 配置JWT工具
 */
@Data
@Component
@ConfigurationProperties(prefix = "vue.jwt.config")
public class JwtUtil {

    // 密钥
    private String secretKey;

    // 单位秒，默认7天
    private long expires = 60*60*24*7;

    /**
     * 生成JWT token
     */
    public String createJWT(String id, String subject, Boolean isLogin){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)    //主题：如用户名
                .setIssuedAt(now)    //签发时间
                .signWith(SignatureAlgorithm.HS256, secretKey)      //签名密钥
                .claim("isLogin", isLogin);
        if(expires > 0){
            builder.setExpiration(new Date(nowMillis + expires*1000));   //设置过期时间
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     */
    public Claims parseJWT(String token){
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }
}
