package com.kingroad.pulsar.auth.bo;

import com.kingroad.pulsar.entity.uo.SysUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:57
 * @Version: v1.0
 * @Description:
 */
@Getter
public class LoginUser extends User {

    private final SysUser user;
    private final List<String> perms;

    public LoginUser(SysUser user, Collection<? extends GrantedAuthority> authorities, List<String> perms) {
        super(user.getUsername(), user.getPasswordHash(), authorities);
        this.user = user;
        this.perms = perms;
    }
}
