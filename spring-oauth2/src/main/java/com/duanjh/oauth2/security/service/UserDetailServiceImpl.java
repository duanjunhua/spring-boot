package com.duanjh.oauth2.security.service;

import com.duanjh.oauth2.security.entity.Login;
import com.duanjh.oauth2.security.entity.Permission;
import com.duanjh.oauth2.security.repo.LoginRepository;
import com.duanjh.oauth2.security.repo.PermissionRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-03 周四 16:58
 * @Version: v1.0
 * @Description: UserDetailsService是SpringSecurity提供用来获取认证用户信息(用户名，密码，用户的权限列表)的接口,可以实现该接口，复写loadUserByUsername(username) 方法加载我们数据库中的用户信息
 *               UserDetails是SpringSecurity用来封装用户认证信息，权限信息的对象
 *
 *  注意：此为方式二（数据库登录），这里定义了UserDetailSerice后，WebSecurityConfig中不在需要定义UserDetailService的Bean需要移除
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Login login = loginRepository.findByUsername(username);

        if(ObjectUtils.isEmpty(login)){
            throw new UsernameNotFoundException("无效的用户名");
        }

        // 加载权限
        List<GrantedAuthority> permissions = new ArrayList<>();
        List<Permission> authorizeds = permissionRepository.findPermissionsByUserId(login.getId());
        if(CollectionUtils.isNotEmpty(authorizeds)){
            authorizeds.stream().forEach(v -> {
                permissions.add(new SimpleGrantedAuthority(v.getExpression()));
            });
        }

        /**
         * 密码是基于BCryptPasswordEncoder加密的密文
         * User是security内部的对象，UserDetails的实现类，用来封装用户的基本信息（用户名，密码，权限列表）
         * 四个true分别是：账户启用、账户过期、密码过期、账户锁定
         */
        return new User(username, login.getPassword(), true, true, true, true, permissions);
    }

}
