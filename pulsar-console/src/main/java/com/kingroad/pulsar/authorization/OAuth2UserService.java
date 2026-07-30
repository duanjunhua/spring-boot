package com.kingroad.pulsar.authorization;

import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 11:23
 * @Version: v1.0
 * @Description: OAuth2 登录处理器：第三方登录自动注册、绑定本地账号
 */
@Slf4j
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Resource
    SysUserService service;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 当前登录的第三方客户端标识：custom-sso
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 获取三方用户唯一标识 oid
        String oauthUserId = oAuth2User.getAttribute("sub");
        String nickname = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        // 查询是否已有绑定账号
        SysUser sysUser = service.findEntityBySsoId(oauthUserId);

        if (sysUser == null) {
            // 自动新建用户
            SysUser u = new SysUser();
            u.setUsername("oauth_" + UUID.randomUUID().toString().substring(0,12));
            u.setPasswordHash("");
            u.setChineseName(nickname);
            u.setEmail(email);
            u.setSsoId(oauthUserId);
            u.setEnable(true);
            sysUser = service.saveOrUpdate(u);
        }

        return oAuth2User;
    }
}
