package com.kingroad.pulsar.authorization.service;

import com.kingroad.pulsar.domain.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 16:43
 * @Version: v1.0
 * @Description:
 */
public class LocalUser extends User {

    private final SysUser u;

    public LocalUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, SysUser u) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.u = u;
    }

    public SysUser getSysUser() {
        return u;
    }
}
