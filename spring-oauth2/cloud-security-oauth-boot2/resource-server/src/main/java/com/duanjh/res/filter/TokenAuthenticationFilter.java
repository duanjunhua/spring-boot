package com.duanjh.res.filter;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-20 周三 16:10
 * @Version: v1.0
 * @Description: 只有使用Zuul网关才需要配置该类：Filter的目的是接收zuul转发过来的明文的Token，绑定到Security上下文中
 *          接收Token的本Filter可以定义在一个公共的模块，其他资源服务直接引入即可，此处直接内置在资源服务中
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 过滤器从请求头中获取到用户授权信息,封装成 UsernamePasswordAuthenticationToken 并设置到securityContext中security在授权的时候会从 UsernamePasswordAuthenticationToken获取认证信息和授权信息进行授权
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //1.获取请求头中的明文token
        String token = httpServletRequest.getHeader("token");

        if(StringUtils.isNotBlank(token)){

            String authToken = new String(Base64Utils.decodeFromString(token));

            Map<String,Object> map = JSON.parseObject(authToken, Map.class);

            // 2.获取到用户主体信息，权限列表
            String username = map.get("principal").toString();

            //权限列表
            List<String> authoritiesStr = (List<String>)map.get("authorities");

            if(CollectionUtils.isEmpty(authoritiesStr)){
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            // 转换权限列表
            List<SimpleGrantedAuthority> authorities = new ArrayList<>(authoritiesStr.size());
            authoritiesStr.forEach(auth ->{
                authorities.add(new SimpleGrantedAuthority(auth));
            });

            // 3.把用户主体信息，权限列表，交给Security把用户信息和权限封装成 UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(username,null, authorities);

            // 设置detail,根据请求对象创建一个detail
            upa.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            // 把UsernamePasswordAuthenticationToken填充到security上下文
            SecurityContextHolder.getContext().setAuthentication(upa);
        }

        //放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
