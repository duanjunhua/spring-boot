package com.kingroad.pulsar.authorization.sso;

import com.kingroad.pulsar.domain.entity.GlobalConfig;
import com.kingroad.pulsar.service.GlobalConfigService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 15:20
 * @Version: v1.0
 * @Description: 动态加载SSO登录配置信息
 */
@Slf4j
@Component
public class SsoClientRegistrationRepository implements ClientRegistrationRepository {

    @Resource
    GlobalConfigService globalConfigService;

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {

        // 根据 registrationId 获取客户端配置（OAuth2回调、跳转授权时核心调用）
        GlobalConfig ssoEnable = globalConfigService.findEntityByConfigKey(SsoConst.SSO_ENABLE);
        if(ObjectUtils.isEmpty(ssoEnable) || StringUtils.equals(ssoEnable.getConfigValue(), SsoConst.INACTIVE)) return null;

        // 构建 Spring Security 标准 ClientRegistration 对象

        String scope = globalConfigService.findValByConfigKey(SsoConst.SCOPE);
        if(StringUtils.isBlank(scope)) {
            scope = SsoConst.DEFAULT_SCOPE;
        }

        String authrizationGrantType = globalConfigService.findValByConfigKey(SsoConst.AUTHORIZATION_GRANT_TYPE);
        if(StringUtils.isBlank(authrizationGrantType)) {
            authrizationGrantType = SsoConst.DEFAULT_AUTHORIZATION_GRANT_TYPE;
        }

        String authrizationMethod = globalConfigService.findValByConfigKey(SsoConst.AUTHORIZATION_METHOD);
        if(StringUtils.isBlank(authrizationMethod)) {
            authrizationMethod = SsoConst.DEFAULT_AUTHORIZATION_METHOD;
        }

        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(globalConfigService.findValByConfigKey(SsoConst.REGISTRATION_ID))
                .clientId(globalConfigService.findValByConfigKey(SsoConst.CLIENT_ID))
                .clientSecret(globalConfigService.findValByConfigKey(SsoConst.CLIENT_SECRET))
                .clientName(globalConfigService.findValByConfigKey(SsoConst.CLIENT_NAME))
                .authorizationGrantType(new AuthorizationGrantType(authrizationGrantType))
                .redirectUri(globalConfigService.findValByConfigKey(SsoConst.REDIRECT_URI))
                .scope(Arrays.stream(scope.split(",")).map(String::trim).collect(Collectors.toList()))
                .clientAuthenticationMethod(new ClientAuthenticationMethod(authrizationMethod));

        // 构造 OIDC Provider 信息
        String issuerUri = globalConfigService.findValByConfigKey(SsoConst.ISSUER_URI);
        if (StringUtils.isNoneBlank(issuerUri)) {
            builder.issuerUri(issuerUri);
        }

        // 如果没有issuer自动发现，手动指定各个端点
        String authorizationUri = globalConfigService.findValByConfigKey(SsoConst.AUTHORIZATION_URI);
        if (StringUtils.isNoneBlank(authorizationUri)) {
            builder.authorizationUri(authorizationUri);
        }

        String tokenUri = globalConfigService.findValByConfigKey(SsoConst.TOKEN_URI);
        if (StringUtils.isNoneBlank(tokenUri)) {
            builder.tokenUri(tokenUri);
        }

        String userInfoUri = globalConfigService.findValByConfigKey(SsoConst.USERINFO_URI);
        if (StringUtils.isNoneBlank(userInfoUri)) {
            builder.userInfoUri(userInfoUri);
        }

        String jwtUri = globalConfigService.findValByConfigKey(SsoConst.JWT_SET_URI);
        if (StringUtils.isNoneBlank(jwtUri)) {
            builder.jwkSetUri(jwtUri);
        }

        return builder.build();
    }
}
