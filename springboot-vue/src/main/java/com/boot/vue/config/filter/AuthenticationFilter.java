package com.boot.vue.config.filter;

import com.boot.vue.config.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-08
 * @Version: V1.0
 * @Description: 拦截器方式实现 token 鉴权
 */
@Component
public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isLogin = false;    //是否登录

        //获取请求头Authorization: Bearer jwtToken
        final String authHeader = request.getHeader("Authorization");

        //判断是否有token，注意Bearer后面有空格
        if(null != authHeader && authHeader.startsWith("Bearer ")){
            //截取获取jwtToken'
            final  String token = StringUtils.substring(authHeader, 7);
            //解析
            Claims claims = jwtUtil.parseJWT(token);
            if(null != claims && claims.get("isLogin", Boolean.class)){
                //已登陆
                isLogin = true;
            }
        }
        if(!isLogin){
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("未通过身份认证");
        }
        return isLogin;
    }
}
