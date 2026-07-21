package com.kingroad.pulsar.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kingroad.pulsar.entity.mr.SysRole;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.mapper.SysRoleMapper;
import com.kingroad.pulsar.mapper.SysUserRoleMapper;
import com.kingroad.pulsar.service.uo.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-17 周五 16:48
 * @Version: v1.0
 * @Description: 第三方登录自动注册
 */
@Slf4j
@Service
public class SsoOAuth2UserService extends DefaultOAuth2UserService {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        log.info("===== 进入自定义CustomOauth2UserService，第三方登录流程开始 =====");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("用户参数：{}", attributes);
        // 1. 获取第三方唯一openId
        String openId = String.valueOf(attributes.get("openid"));
        String unionId = String.valueOf(attributes.get("name"));
        String avatar = String.valueOf(attributes.get("avatar_url"));

        // 2. 查询是否已有第三方用户
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUserId, openId);
        SysUser exist = sysUserService.getOne(wrapper);
        SysUser sysUser;

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
        }else{
            // 已有绑定，查询本地用户
            sysUser = sysUserService.getById(exist.getUserId());
        }
        // 封装权限
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new DefaultOAuth2User(authorities, attributes, "sub");
    }

}
