package com.kingroad.pulsar.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kingroad.pulsar.entity.mr.SysRole;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.mapper.SysPermissionMapper;
import com.kingroad.pulsar.mapper.SysRoleMapper;
import com.kingroad.pulsar.mapper.SysUserRoleMapper;
import com.kingroad.pulsar.service.uo.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-17 周五 16:18
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
public class SsoOidcUserServiceImpl extends OidcUserService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("===== 进入自定义SsoOidcUserServiceImpl，第三方登录流程开始 =====");

        OidcUser oidcUser = super.loadUser(userRequest);
        // SSO唯一标识
        String unionId = oidcUser.getSubject();

        // 预查询本地是否存在该用户，交给登录成功处理器跳转申请页
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUserId, unionId);
        SysUser exist = sysUserService.getOne(wrapper);

        SysUser sysUser;
        List<SimpleGrantedAuthority> authorities;

        // 不存在则新建第三方用户
        if (exist == null) {
            sysUser = new SysUser();
            sysUser.setUsername("oauth_" + unionId);
            sysUser.setUserId(unionId);
            sysUser.setPulsarClusterId(1);
            sysUser.setTenantName("zevent");
            sysUser.setIsSuperAdmin(Boolean.FALSE);
            sysUser.setEnable(true);
            sysUserService.save(sysUser);
            // 5. 分配默认普通用户角色 ROLE_USER
            LambdaQueryWrapper<SysRole> roleWrapper = new LambdaQueryWrapper<>();
            roleWrapper.eq(SysRole::getRoleCode, "ROLE_USER");
            SysRole userRole = sysRoleMapper.selectOne(roleWrapper);
            // 用户角色关联插入
            userRoleMapper.insert(com.kingroad.pulsar.entity.mr.SysUserRole.builder().userId(sysUser.getId()).roleId(userRole.getId()).build());
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }else{
            // 已有绑定，查询本地用户
            sysUser = sysUserService.getById(exist.getUserId());
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), "sub");
    }

}
