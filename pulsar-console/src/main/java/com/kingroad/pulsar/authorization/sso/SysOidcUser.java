package com.kingroad.pulsar.authorization.sso;

import com.kingroad.pulsar.domain.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Collection;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-31 周五 11:28
 * @Version: v1.0
 * @Description: 自定义OidcUser用于SSO登录封装
 */
public class SysOidcUser extends DefaultOidcUser {

    private final SysUser u;

    public SysOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo, String nameAttributeKey, SysUser sysUser) {
        super(authorities, idToken, nameAttributeKey);
        this.u = sysUser;
    }

    public SysUser getSysUser() {
        return u;
    }
}
