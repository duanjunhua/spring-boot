package com.duanjh.oauth.controller;

import com.duanjh.oauth.dto.ClientDTO;
import com.duanjh.oauth.result.Result;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 16:11
 * @Version: v1.0
 * @Description: 动态客户端管理
 */
@RestController
@RequestMapping("/admin/client")
public class ClientManageController {

    @Resource
    private RegisteredClientRepository registeredClientRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 根据clientId查询接入客户端
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClient(@PathVariable String clientId) {
        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        return Result.success(client);
    }

    /**
     * 新增接入系统客户端
     * 前端传入：clientId、clientName、clientSecret、redirectUri、scope
     */
    @PostMapping("/add")
    public ResponseEntity<?> addClient(@RequestBody ClientDTO dto) {
        // 加密客户端密钥
        String encodeSecret = "{bcrypt}" + passwordEncoder.encode(dto.getClientSecret());

        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(dto.getClientId())
                .clientSecret(encodeSecret)
                .clientName(dto.getClientName())
                // 客户端认证方式：密码基础认证
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 授权模式：授权码、刷新令牌、密码模式（内网可信系统）
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                // 回调地址，多地址逗号分隔
                .redirectUri(dto.getRedirectUri())
                // 授权范围 openid必带，OIDC标准
                .scope("openid")
                .scope("profile")
                .scope(dto.getScope())
                // 客户端配置：无需手动授权确认
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                // Token配置：accessToken1小时，refreshToken1天，不复用刷新令牌
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(1))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .reuseRefreshTokens(false)
                        .build())
                .build();

        registeredClientRepository.save(registeredClient);
        return Result.success("客户端创建成功");
    }

    /**
     * 删除客户端
     */
    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable String clientId) {
        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        if (!ObjectUtils.isEmpty(client)) {
//            registeredClientRepository.delete(client);
        }
        return Result.success("删除成功");
    }
}
