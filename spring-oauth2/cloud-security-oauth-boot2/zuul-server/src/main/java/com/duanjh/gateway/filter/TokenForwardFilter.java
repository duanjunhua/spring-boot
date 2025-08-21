package com.duanjh.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.*;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-20 周三 15:52
 * @Version: v1.0
 * @Description: 转发认证信息
 *              定义filter,从上下文中拿到认证信息，授权信息，封装成 JSON，通过请求头转发给下游微服务
 */
@Component
public class TokenForwardFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 启用
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {

        //1.拿到上下文中的认证对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //2.拿到认证对象中的用户信息
        if(!(authentication instanceof OAuth2Authentication)){
            return null;
        }

        // 获取用户授权信息
        Map<String, Object> map = getAuthorities(authentication);

        //5.把JSON设置到请求头传给下游微服务
        byte[] header = new ObjectMapper().writeValueAsBytes(map);

        String jsonToken = Base64Utils.encodeToString(header) ;

        RequestContext.getCurrentContext().addZuulRequestHeader("token", jsonToken);

        return null;
    }

    private static Map<String, Object> getAuthorities(Authentication authentication) {

        // 用户主体，包含用户别名
        Object principal = authentication.getPrincipal();

        //请求参数
        Map<String, String> requestParameters = ((OAuth2Authentication) authentication).getOAuth2Request().getRequestParameters();

        //3.拿到认证对象中的权限列表
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        //转一下权限列表
        List<String> authoritiesList = new ArrayList<>(authorities.size());

        authorities.forEach(authority ->{
            authoritiesList.add(authority.getAuthority());
        });

        //4.把用户信息和权限列表封装成map，转成JSON
        Map<String,Object> map = new HashMap<>(requestParameters);
        map.put("principal",principal);
        map.put("authorities",authoritiesList);

        return map;
    }
}
