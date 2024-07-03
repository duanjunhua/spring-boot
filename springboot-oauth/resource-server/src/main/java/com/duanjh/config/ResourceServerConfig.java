package com.duanjh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * @Author: Michael J H Duan[Junhua Duan]
 * @Date: 2023-05-16 10:59
 * @Version: V1.0
 * @Description: 资源服务配置
 * 作为资源服务的配置类必须满足两个条件
 * 	标注注解@EnableResourceServer
 * 	继承ResourceServerConfigurerAdapter
 */
@Configuration
@EnableResourceServer   //标记这是一个资源服务
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)     //开启注解校验权限
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    /**
     * 配置令牌校验服务：客户端携带令牌访问资源，作为资源端必须校验令牌的真伪
     * TODO： 使用JWT作为TOKEN则不必远程调用check_token校验
     */
    @Bean
    public RemoteTokenServices tokenServices(){
        // 远程调用授权服务的check_token进行令牌的校验
        RemoteTokenServices services = new RemoteTokenServices();
        // /oauth/check_token 这个url是认证中心校验的token的端点
        services.setCheckTokenEndpointUrl("http://localhost/oauth-server/oauth/check_token");
        //客户端唯一ID
        services.setClientId("duanjh-client");
        //客户端密钥
        services.setClientSecret("duanjh-secret");
        return services;
    }

    /**
     * 配置资源ID和令牌校验服务
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 配置唯一资源id
        resources.resourceId("duanjh-res")
                //配置令牌校验服务
                .tokenServices(tokenServices());
    }

    /**
     * 配置Security的安全机制
     *  资源服务可以根据这个客户端的scope进行url的拦截，拦截方式如下：
     *      .access("#oauth2.hasScope('')")
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 表示所有路径都需要all的权限
            .antMatchers("/**").access("#oauth2.hasScope('all')")
            .anyRequest().authenticated();
    }
}
