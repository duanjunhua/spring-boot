package com.kingroad.pulsar.auth.service;

import com.kingroad.pulsar.constant.CommonConst;
import com.kingroad.pulsar.service.config.SysConfigService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-17 周五 17:26
 * @Version: v1.0
 * @Description:
 */
@Repository
public class SsoClientRegistrationRepository implements ClientRegistrationRepository, OAuth2AuthorizationRequestResolver {

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private SysConfigService configService;

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        return convertToClientRegistration();
    }

    private ClientRegistration convertToClientRegistration() {
        return ClientRegistration.withRegistrationId("pulsar-console-oauth")
                .clientId(configService.getConfigValue(CommonConst.OAUTH_CLIENT_ID))
                .clientSecret(configService.getConfigValue(CommonConst.OAUTH_CLIENT_SECRET))
                .authorizationUri(configService.getConfigValue(CommonConst.OAUTH_AUTHORIZATION_URI))
                .tokenUri(configService.getConfigValue(CommonConst.OAUTH_TOKEN_URI))
                .userInfoUri(configService.getConfigValue(CommonConst.OAUTH_USER_INFO_URI))
                .scope("openid", "profile")
                .issuerUri("http://127.0.0.1:9000/auth")
                .userNameAttributeName("sub")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(configService.getConfigValue(CommonConst.OAUTH_REDIRECT_URI))
                .build();
    }

    // 2. 新增：实现OAuth2AuthorizationRequestResolver，修复动态客户端state/回调解析
    private final OAuth2AuthorizationRequestResolver delegate = new DefaultOAuth2AuthorizationRequestResolver(this, "/oauth2/authorization");

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        return delegate.resolve(request);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        return delegate.resolve(request, clientRegistrationId);
    }
}
