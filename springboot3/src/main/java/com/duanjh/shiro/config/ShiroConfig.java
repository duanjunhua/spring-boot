package com.duanjh.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-12 周四 09:35
 * @Version: v1.0
 * @Description: Shiro配置
 *      Apache Shiro 核心通过 Filter 来实现，就好像 SpringMvc 通过 DispachServlet，
 *   是通过 URL 规则来进行过滤和权限校验，需要定义一系列关于 URL 的规则和访问权限
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        log.info("ShiroConfig filter");

        ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();
        filterBean.setSecurityManager(securityManager);

        /*--------------- 拦截器 ---------------*/
        Map<String, String> map = new LinkedHashMap<>();

        // 配置不会拦截的链接，顺序判断
        map.put("/static/**", "anon");
        map.put("/login", "anon");
        map.put("/loginPage", "anon");

        // 配置退出过滤器
        map.put("/logout", "logout");

        /**
         * 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
         * authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
         */
        map.put("/**", "authc");

        // 配置登录，若不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        filterBean.setLoginUrl("/loginPage");

        // 配置登录成功后跳转的链接
        filterBean.setSuccessUrl("/index");

        // 未授权界面
        filterBean.setUnauthorizedUrl("/unauthorized");

        filterBean.setFilterChainDefinitionMap(map);

        return filterBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 设置自定义的Realm
        securityManager.setRealm(personalMd5Realm());
        return securityManager;
    }

    /**
     * 不加密的Realm
     */
    @Bean(name = "customRealm")
    public PersonalRealm personalCustomRealm() {
        return new PersonalRealm();
    }

    /**
     * MD5加解密的Realm
     */
    @Bean(name = "md5Realm")
    public PersonalRealm personalMd5Realm(){
        PersonalRealm realm = new PersonalRealm();

        // 1. 创建hashed的凭证匹配器
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();

        // 2. 设置 md5 加密算法
        matcher.setHashAlgorithmName("md5");

        // 3. 设置散列次数
        matcher.setHashIterations(1024);

        // 4.  设置hashed的凭证匹配器
        realm.setCredentialsMatcher(matcher);

        return realm;
    }

    /**
     * 开启Shiro注解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 认证或权限异常处理
     *  Shiro中若使用注释来注入角色或权限的话，无法抛出UnauthorizedException异常，需要使用SimpleMappingExceptionResolver处理，
     *  若不添加，会跳转至默认的error.html，通过自定义可跳转指定页面
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        // 注意/unauthorized需要有对应的页面名称，如：unauthorized.html
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/unauthorized");
        resolver.setExceptionMappings(properties);
        return resolver;
    }
}
