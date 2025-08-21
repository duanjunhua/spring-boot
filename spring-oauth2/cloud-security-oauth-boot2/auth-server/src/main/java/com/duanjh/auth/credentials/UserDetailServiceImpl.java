package com.duanjh.auth.credentials;

import com.duanjh.auth.entity.Permission;
import com.duanjh.auth.entity.User;
import com.duanjh.auth.repo.PermissionRepository;
import com.duanjh.auth.repo.UserRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-08 周五 15:39
 * @Version: v1.0
 * @Description: 实现UserDetailsService接口，复写loadUserByUsername，根据用户名从数据库中获取认证的用户对象，
 *               并且加载用户的权限信息，将用户的认证信息和权限信息封装成UserDetaials返回
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    /**
     * 认证服务在处理认证逻辑的时候会通过该userDetailService加载用户在数据库中的认证信息实现身份认证，同时在整个方法中
     * 也加载了用户的权限列表，后续当用户发起对资源服务的访问时，是否能够授权成功就跟用户的权限列表息息相关
     *
     * 加载数据库中的认证的用户的信息：用户名，密码，用户的权限列表
     * @param username 用户登录名
     * @return 封装成 UserDetails进行返回 ，交给security
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * 查询用户是否存在
         */
        User user = userRepository.findByUsername(username);
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("无效的用户");
        }

        String password = user.getPassword();

        // 查询用户权限
        List<Permission> permission = permissionRepository.findByUserId(user.getId());

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(permission)){
            // 用户权限加载
            permission.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getExpression())));
        }

        /**
         * User是security内部的对象，UserDetails的实现类，用来封装用户的基本信息（用户名，密码，权限列表）
         * User(String username, String password, Collection<? extends GrantedAuthority> authorities)
         */
        return new org.springframework.security.core.userdetails.User(username, password, authorities);
    }

}
