-- GoNavi SQL Export
-- Time: 2026-07-31 12:14:21
-- Database: pulsar_console


-- ----------------------------
-- Table: public.t_global_config
-- ----------------------------

CREATE TABLE "public"."t_global_config" (
  "id" bigint NOT NULL DEFAULT nextval('t_global_config_id_seq'::regclass),
  "config_key" character varying(100) NOT NULL,
  "config_value" text NOT NULL,
  "description" text,
  "create_at" timestamp without time zone DEFAULT now(),
  "update_at" timestamp without time zone DEFAULT now(),
  PRIMARY KEY ("id")
);

INSERT INTO "public"."t_global_config" ("id", "config_key", "config_value", "description", "create_at", "update_at") VALUES (1, 'sso.enable', '1', '', '2026-07-30 14:33:49', '2026-07-30 14:33:49');
INSERT INTO "public"."t_global_config" ("id", "config_key", "config_value", "description", "create_at", "update_at") VALUES (2, 'sso.token_uri', 'http://127.0.0.1:9000/auth/oauth2/token', '', '2026-07-30 16:14:20', '2026-07-30 16:14:20');
INSERT INTO "public"."t_global_config" ("id", "config_key", "config_value", "description", "create_at", "update_at") VALUES (3, 'sso.client_id', 'pulsar-console-oauth', '', '2026-07-30 16:15:01', '2026-07-30 16:15:01');
INSERT INTO "public"."t_global_config" ("id", "config_key", "config_value", "description", "create_at", "update_at") VALUES (5, 'sso.authorization_uri', 'http://127.0.0.1:9000/auth/oauth2/authorize', '', '2026-07-30 16:16:13', '2026-07-30 16:16:13');
INSERT INTO "public"."t_global_config" ("id", "config_key", "config_value", "description", "create_at", "update_at") VALUES (6, 'sso.redirect_uri', 'http://127.0.0.1:8080/login/oauth2/code/pulsar-console-oauth', '', '2026-07-30 16:16:13', '2026-07-30 16:16:13');
INSERT INTO "public"."t_global_config" ("id", "config_key", "config_value", "description", "create_at", "update_at") VALUES (7, 'sso.registration_id', 'local_sso', '', '2026-07-30 16:16:59', '2026-07-30 16:16:59');
INSERT INTO "public"."t_global_config" ("id", "config_key", "config_value", "description", "create_at", "update_at") VALUES (4, 'sso.client_secret', 'Pulsar@Console123456', '', '2026-07-30 16:16:13', '2026-07-30 16:16:13');
INSERT INTO "public"."t_global_config" ("id", "config_key", "config_value", "description", "create_at", "update_at") VALUES (8, 'sso.jwt_set_uri', 'http://127.0.0.1:9000/auth/oauth2/jwks', '', '2026-07-31 09:22:58', '2026-07-31 09:22:58');
