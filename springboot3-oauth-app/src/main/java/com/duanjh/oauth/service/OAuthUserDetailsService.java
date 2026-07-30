package com.duanjh.oauth.service;

import com.duanjh.oauth.entity.SysAuthority;
import com.duanjh.oauth.entity.SysUser;
import com.duanjh.oauth.repository.SysAuthorityRepository;
import com.duanjh.oauth.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 15:51
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
public class OAuthUserDetailsService implements UserDetailsService {

    @Autowired
    SysUserRepository userRepository;

    @Autowired
    SysAuthorityRepository auditRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("开始加载登录用户信息，用户名：{}", username);
        // 查询用户，不存在抛出异常
        SysUser sysUser = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("用户名【{}】不存在", username);
                    return new UsernameNotFoundException("登录账号不存在，请检查用户名");
                });

        // 查询用户绑定的所有权限/角色
        List<SysAuthority> authorityList = auditRepository.findByUserId(sysUser.getId());

        // 组装权限集合
        Collection<GrantedAuthority> grantedAuthorities = authorityList.stream()
                .map(SysAuthority::getAuthority)
                // 如需角色鉴权：数据库无ROLE\_前缀放开下面代码
                // .map(auth -> auth.startsWith("ROLE\_") ? auth : "ROLE\_" + auth)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 利用Security内置User构建UserDetails，替代匿名内部类，代码简洁
        return User.withUsername(sysUser.getUsername())
                .password(sysUser.getPassword())
                .authorities(grantedAuthorities)
                .accountExpired(!sysUser.getAccountNonExpired())
                .accountLocked(!sysUser.getAccountNonLocked())
                .credentialsExpired(!sysUser.getCredentialsNonExpired())
                .disabled(!sysUser.getEnabled())
                .build();
    }
}
