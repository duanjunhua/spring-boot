-- 创建数据库
CREATE DATABASE sso_auth;
\c sso_auth;

-- 1. OAuth2客户端表（接入SSO的第三方系统）
CREATE TABLE oauth2_registered_client (
    id VARCHAR(100) PRIMARY KEY,
    client_id VARCHAR(100) NOT NULL UNIQUE,
    client_id_issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    client_secret VARCHAR(200),
    client_secret_expires_at TIMESTAMP,
    client_name VARCHAR(200) NOT NULL,
    client_authentication_methods VARCHAR(1000) NOT NULL,
    authorization_grant_types VARCHAR(1000) NOT NULL,
    redirect_uris VARCHAR(1000),
    post_logout_redirect_uris VARCHAR(1000),
    scopes VARCHAR(1000) NOT NULL,
    client_settings VARCHAR(2000) NOT NULL,
    token_settings VARCHAR(2000) NOT NULL
);

-- 2. 授权同意记录表（用户授权第三方客户端）
CREATE TABLE oauth2_authorization_consent (
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name VARCHAR(200) NOT NULL,
    authorities VARCHAR(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);

-- 3. 授权存储表（存储code、access_token、refresh_token）
CREATE TABLE oauth2_authorization (
    id VARCHAR(100) PRIMARY KEY,
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name VARCHAR(200) NOT NULL,
    authorization_grant_type VARCHAR(100) NOT NULL,
    authorized_scopes VARCHAR(1000),
    attributes TEXT,
    state VARCHAR(500),
    authorization_code_value VARCHAR(1000),
    authorization_code_issued_at TIMESTAMP,
    authorization_code_expires_at TIMESTAMP,
    authorization_code_metadata TEXT,
    access_token_value VARCHAR(1000),
    access_token_issued_at TIMESTAMP,
    access_token_expires_at TIMESTAMP,
    access_token_metadata TEXT,
    access_token_type VARCHAR(100),
    access_token_scopes VARCHAR(1000),
    refresh_token_value VARCHAR(1000),
    refresh_token_issued_at TIMESTAMP,
    refresh_token_expires_at TIMESTAMP,
    refresh_token_metadata TEXT,
    oidc_id_token_value VARCHAR(1000),
    oidc_id_token_issued_at TIMESTAMP,
    oidc_id_token_expires_at TIMESTAMP,
    oidc_id_token_metadata TEXT,
    user_code_value VARCHAR(1000),
    user_code_issued_at TIMESTAMP,
    user_code_expires_at TIMESTAMP,
    user_code_metadata TEXT,
    device_code_value VARCHAR(1000),
    device_code_issued_at TIMESTAMP,
    device_code_expires_at TIMESTAMP,
    device_code_metadata TEXT
);

-- 4. 用户表（认证账号）
CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    real_name VARCHAR(50),
    email VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. 用户角色权限表
CREATE TABLE sys_authority (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL
);

-- 测试客户端（演示系统A）
INSERT INTO oauth2_registered_client (
    id,client_id,client_secret,client_name,client_authentication_methods,
    authorization_grant_types,redirect_uris,scopes,client_settings,token_settings
) VALUES (
    'client-pulsar-console',
    'pulsar-console',
    '{bcrypt}$2a$10$xJwL5v1uzT5Z0p2y7tG6KuX7t0Q8r1FzH5j9k0v1X7zY9a1b2c3d4',
    '业务系统A',
    'client_secret_basic',
    'authorization_code,refresh_token,password',
    'http://127.0.0.1:8080',
    'openid,profile,user:read,offline_access',
    '{"settings.requireAuthorizationConsent":false,"settings.jwkSetEndpointEnabled":false}',
    '{"accessTokenTimeToLive":3600,"refreshTokenTimeToLive":86400,"reuseRefreshTokens":false}'
);

-- 测试用户 账号admin 密码123456
INSERT INTO sys_user(username,password,real_name)
VALUES ('admin','{bcrypt}$2a$10$xJwL5v1uzT5Z0p2y7tG6KuX7t0Q8r1FzH5j9k0v1X7zY9a1b2c3d4','超级管理员');

INSERT INTO sys_authority(username,authority) VALUES
('admin','ROLE_ADMIN'),
('admin','USER:READ');