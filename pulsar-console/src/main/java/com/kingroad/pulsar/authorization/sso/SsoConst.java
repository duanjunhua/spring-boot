package com.kingroad.pulsar.authorization.sso;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 15:22
 * @Version: v1.0
 * @Description:
 */
public class SsoConst {

    /**
     * SSO登录唯一标识，如：sso、dingtalk、wechat
     */
    public static final String SSO_ENABLE = "sso.enable";
    // 启用SSO
    public static final String ACTIVE = "1";
    // 禁用SSO
    public static final String INACTIVE = "0";

    /**
     * SSO登录唯一标识，如：sso、dingtalk、wechat
     */
    public static final String REGISTRATION_ID = "sso.registration_id";

    /**
     * 客户端ID
     */
    public static final String CLIENT_ID = "sso.client_id";

    /**
     * 客户端密钥
     */
    public static final String CLIENT_SECRET = "sso.client_secret";

    /**
     * 客户端密钥
     */
    public static final String CLIENT_NAME = "sso.client_name";

    /**
     * 登陆后重定向地址
     */
    public static final String REDIRECT_URI = "sso.redirect_uri";

    /**
     * 授权范围
     */
    public static final String SCOPE = "sso.scope";

    /**
     * 授权类型
     */
    public static final String AUTHORIZATION_GRANT_TYPE = "sso.authorization_grant_type";

    /**
     * 授权方式
     */
    public static final String AUTHORIZATION_METHOD = "sso.authorization_method";

    /**
     * sso base uri 地址
     */
    public static final String ISSUER_URI = "sso.issuer_uri";

    /**
     * 授权地址
     */
    public static final String AUTHORIZATION_URI = "sso.authorization_uri";

    /**
     * 令牌地址
     */
    public static final String TOKEN_URI = "sso.token_uri";

    /**
     * 用户信息地址
     */
    public static final String USERINFO_URI = "sso.userinfo_uri";

    /**
     * jwt地址
     */
    public static final String JWT_SET_URI = "sso.jwt_set_uri";

    /**
     * 默认授权码
     */
    public static final String DEFAULT_AUTHORIZATION_GRANT_TYPE = "authorization_code";

    /**
     * 默认授权方式
     */
    public static final String DEFAULT_AUTHORIZATION_METHOD = "client_secret_basic";

    /**
     * 默认授权范围
     */
    public static final String DEFAULT_SCOPE = "openid,profile,user:read";

    /**
     * SSO /userinfo拉取用户
     */
    public static final String USERINFO_ENDPOINT_ENABLED = "userinfo_endpoint_enabled";

    /**
     * SSO客户端属性
     */
    public static final String ATTR_SUB = "sub";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_EMAIL = "email";
}
