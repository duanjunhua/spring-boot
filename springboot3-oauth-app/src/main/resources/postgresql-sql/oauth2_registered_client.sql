-- GoNavi SQL Export
-- Time: 2026-07-31 12:16:14
-- Database: sso_auth


-- ----------------------------
-- Table: public.oauth2_registered_client
-- ----------------------------

CREATE TABLE "public"."oauth2_registered_client" (
  "id" character varying(100) NOT NULL,
  "client_id" character varying(100) NOT NULL,
  "client_id_issued_at" timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "client_secret" character varying(200),
  "client_secret_expires_at" timestamp without time zone,
  "client_name" character varying(200) NOT NULL,
  "client_authentication_methods" character varying(1000) NOT NULL,
  "authorization_grant_types" character varying(1000) NOT NULL,
  "redirect_uris" character varying(1000),
  "post_logout_redirect_uris" character varying(1000),
  "scopes" character varying(1000) NOT NULL,
  "client_settings" character varying(2000) NOT NULL,
  "token_settings" character varying(2000) NOT NULL,
  PRIMARY KEY ("id")
);

INSERT INTO "public"."oauth2_registered_client" ("id", "client_id", "client_id_issued_at", "client_secret", "client_secret_expires_at", "client_name", "client_authentication_methods", "authorization_grant_types", "redirect_uris", "post_logout_redirect_uris", "scopes", "client_settings", "token_settings") VALUES ('local_sso', 'pulsar-console-oauth', '2026-07-30 17:18:47', '$2a$10$zP.Qg3J2qpM7yN0mvHPB9uuetH18U7FLUyA3ml8lQJ.cBt4IYhDvC', NULL, '本地SSO认证', 'client_secret_basic', 'refresh_token,client_credentials,password,authorization_code', 'http://127.0.0.1:8080/login/oauth2/code/pulsar-console-oauth', '', 'openid,profile,user:read,openid,profile,user:read', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}');
