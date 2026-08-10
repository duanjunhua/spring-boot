package com.kingroad.pulsar.authorization.service;

import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 11:32
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
public class LocalUserDetailService implements UserDetailsService {

    @Resource
    SysUserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser u = service.findEntityByUsername(username);

        if(ObjectUtils.isEmpty(u)) throw new UsernameNotFoundException("用户名不存在");

        if(StringUtils.isNoneBlank(u.getSsoId())) throw new BusinessException("请通过第三方SSO登录");

        return getUserDetails(u);
    }

    /**
     * 根据第三方唯一账号查询本地用户
     * @param ssoId 第三方唯一标识 sub / email
     * @return 用户信息，不存在返回null
     */
    public UserDetails loadUserBySsoId(String ssoId){
        SysUser u = service.findEntityBySsoId(ssoId);

        if(ObjectUtils.isEmpty(u)) throw new UsernameNotFoundException("用户名不存在");

        return getUserDetails(u);
    }

    private static @NonNull User getUserDetails(SysUser u) {
        return new LocalUser(
                u.getUsername(),
                u.getPasswordHash(),
                u.getEnable(),
                true,
                true,
                true,
                u.getRoleList().stream().map(SysRole::getRoleCode).map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                u
        );
    }
}
