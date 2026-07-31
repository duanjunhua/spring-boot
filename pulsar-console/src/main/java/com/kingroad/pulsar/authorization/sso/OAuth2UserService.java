package com.kingroad.pulsar.authorization.sso;

import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.domain.entity.SysUserRole;
import com.kingroad.pulsar.repository.SysUserRoleRepository;
import com.kingroad.pulsar.service.SysRoleService;
import com.kingroad.pulsar.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 11:23
 * @Version: v1.0
 * @Description: OAuth2 登录处理器：第三方登录自动注册、绑定本地账号，与OidcUserService二选一即可
 */
@Slf4j
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Resource
    SysUserService service;

    @Resource
    SysUserRoleRepository urRepository;

    @Resource
    SysRoleService roleService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 当前登录的第三方客户端标识：custom-sso
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 获取三方用户唯一标识 oid
        String oauthUserId = oAuth2User.getAttribute(SsoConst.ATTR_SUB);
        String nickname = oAuth2User.getAttribute(SsoConst.ATTR_NAME);
        String email = oAuth2User.getAttribute(SsoConst.ATTR_EMAIL);

        // 查询是否已有绑定账号
        SysUser sysUser = service.findEntityBySsoId(oauthUserId);

        if (sysUser == null) {
            // 自动新建用户
            SysUser u = SysUser.buildSsoUser(oauthUserId, email, nickname);
            sysUser = service.saveOrUpdate(u);

            // 给予用户普通角色
            SysRole role = roleService.findEntityByRoleCode("ROLE_USER");
            if(ObjectUtils.isNotEmpty(role)){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(role.getId());
                sysUserRole.setUserId(sysUser.getId());
                urRepository.save(sysUserRole);
            }
            sysUser.setRoleList(Arrays.asList(role));
        }

        // 把用户权限封装成OAuth2User，供Security上下文使用
        return new DefaultOAuth2User(
                sysUser.getRoleList().stream().map(SysRole::getRoleCode)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes(),
                oauthUserId
        );
    }
}
