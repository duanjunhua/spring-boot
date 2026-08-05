package com.kingroad.pulsar.authorization.filter;

import com.kingroad.pulsar.util.EncryptUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-05 周三 15:04
 * @Version: v1.0
 * @Description: 前端传 RSA 密文密码，后端过滤器解密出原始明文再走正常认证流程
 */
public class PreUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String rsaPrivateKey;

    public PreUsernamePasswordAuthenticationFilter(String rsaPrivateKey){
        this.rsaPrivateKey = rsaPrivateKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 1. 获取前端传过来的密文
        String username = obtainUsername(request);
        String cipherPwd = obtainPassword(request);
        String rawPassword = EncryptUtil.decryptWithRsa(cipherPwd, EncryptUtil.getRsaPrivateKey(rsaPrivateKey));

        if(StringUtils.isBlank(rawPassword)){
            throw new BadCredentialsException("密码解密失败，请重试");
        }

        // 2. 使用【解密后的明文密码】构造认证Token
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, rawPassword);

        // 复制请求详情（ip、session等）
        setDetails(request, authRequest);

        // 交给AuthenticationManager进行认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
