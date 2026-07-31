-- GoNavi SQL Export
-- Time: 2026-07-31 12:15:03
-- Database: pulsar_console


-- ----------------------------
-- Table: public.t_role
-- ----------------------------

CREATE TABLE "public"."t_role" (
  "id" bigint NOT NULL DEFAULT nextval('t_role_id_seq'::regclass),
  "role_name" character varying(100) NOT NULL,
  "role_code" character varying(100) NOT NULL,
  "description" text,
  "create_at" timestamp without time zone DEFAULT now(),
  "update_at" timestamp without time zone DEFAULT now(),
  PRIMARY KEY ("id")
);

INSERT INTO "public"."t_role" ("id", "role_name", "role_code", "description", "create_at", "update_at") VALUES (1, '超级管理员', 'SUPER_ADMIN', '拥有系统所有权限', '2026-07-30 14:43:50', '2026-07-30 14:43:50');
INSERT INTO "public"."t_role" ("id", "role_name", "role_code", "description", "create_at", "update_at") VALUES (2, '系统管理员', 'ADMIN', '系统管理与运维权限', '2026-07-30 14:44:42', '2026-07-30 14:44:42');
INSERT INTO "public"."t_role" ("id", "role_name", "role_code", "description", "create_at", "update_at") VALUES (3, '普通用户', 'ROLE_USER', '', '2026-07-31 10:58:26', '2026-07-31 10:58:26');
