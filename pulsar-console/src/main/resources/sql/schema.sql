CREATE DATABASE pulsar_console;

\c pulsar_console;

CREATE TABLE t_global_config (
    id BIGSERIAL PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT NOT NULL ,
    description TEXT,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_global_config is '全局配置表';
COMMENT ON COLUMN t_global_config.id IS '主键';
COMMENT ON COLUMN t_global_config.config_key IS '配置项键名，全局唯⼀ （如：sso.enable：是否开启sso， 0-关闭 1-开启、init_admin_flag：是否已完成初始化引导 0-未初始化 1-已初始化等';
COMMENT ON COLUMN t_global_config.config_value IS '配置项值 (JSON 格式存储复杂对象)';
COMMENT ON COLUMN t_global_config.description IS '配置项描述';
COMMENT ON COLUMN t_global_config.create_at IS '创建时间';
COMMENT ON COLUMN t_global_config.update_at IS '更新时间';
-- 预置key说明
    -- init_admin_flag：是否已完成初始化引导 0未初始化 1已初始化
    -- admin_initial_pwd：初始化设置的超级管理员密码（加密存储）
    -- sso.enable：true/false 是否开启第三方OAuth2 SSO
    -- oauth.client_id：第三方客户端ID
    -- oauth.client_secret：客户端密钥
    -- oauth.authorization_uri：授权地址
    -- oauth.token_uri：令牌地址
    -- oauth.user_info_uri：用户信息获取地址
    -- oauth_redirect_uri：回调地址

CREATE TABLE t_pulsar_cluster (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    service_url VARCHAR(256) NOT NULL,
    admin_api_url VARCHAR(256) NOT NULL,
    auth_plugin  VARCHAR(128) DEFAULT NULL,
    auth_params  TEXT DEFAULT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(16) NOT NULL,
    description TEXT DEFAULT NULL,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_pulsar_cluster is 'Pulsar集群表';
COMMENT ON COLUMN t_pulsar_cluster.id IS '主键';
COMMENT ON COLUMN t_pulsar_cluster.name IS '集群名称 (如 prod-cluster , staging-cluster )';
COMMENT ON COLUMN t_pulsar_cluster.service_url IS 'Pulsar 服务 URL (如 pulsar://pulsar.example.com:6650 )';
COMMENT ON COLUMN t_pulsar_cluster.admin_api_url IS 'Pulsar Admin API URL (如 http://pulsar-admin.example.com:8080 )';
COMMENT ON COLUMN t_pulsar_cluster.auth_plugin IS '认证插件类名 (如org.apache.pulsar.client.impl.auth.AuthenticationToken）';
COMMENT ON COLUMN t_pulsar_cluster.auth_params IS '认证参数 (如 JWT token 或 JSON 格式的密钥信息)';
COMMENT ON COLUMN t_pulsar_cluster.is_default IS '是否为默认集群';
COMMENT ON COLUMN t_pulsar_cluster.status IS '集群状态 (ACTIVE/INACTIVE)';
COMMENT ON COLUMN t_pulsar_cluster.description IS '集群描述';
COMMENT ON COLUMN t_pulsar_cluster.create_at IS '创建时间';
COMMENT ON COLUMN t_pulsar_cluster.update_at IS '更新时间';

CREATE TABLE t_user (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(64) NOT NULL UNIQUE,
    username VARCHAR(128) NOT NULL,
    chinese_name VARCHAR(256) DEFAULT NULL,
    tenant_name VARCHAR(32) NOT NULL,
    pulsar_cluster_id BIGINT NOT NULL,
    is_super_admin BOOLEAN NOT NULL DEFAULT FALSE,
    password_hash VARCHAR(32) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    last_login_time TIMESTAMP,
    enable BOOLEAN NOT NULL DEFAULT TRUE,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_user is '⽤⼾表';
COMMENT ON COLUMN t_user.id IS '主键';
COMMENT ON COLUMN t_user.user_id IS '配SSO 平台的唯⼀⽤⼾ID';
COMMENT ON COLUMN t_user.username IS '⽤⼾显⽰名';
COMMENT ON COLUMN t_user.chinese_name IS '⽤⼾中文名';
COMMENT ON COLUMN t_user.tenant_name IS '对应 Pulsar 租⼾名，全局唯⼀';
COMMENT ON COLUMN t_user.pulsar_cluster_id IS '关联的 Pulsar 集群ID (外键)';
COMMENT ON COLUMN t_user.is_super_admin IS '是否为超级管理员，TRUE: 超级管理员 FALSE: 普通用户';
COMMENT ON COLUMN t_user.password_hash IS '仅超级管理员使⽤，MD5 加密后的密码摘要';
COMMENT ON COLUMN t_user.phone IS '手机号';
COMMENT ON COLUMN t_user.email IS '邮箱';
COMMENT ON COLUMN t_user.last_login_time IS '最近登录时间';
COMMENT ON COLUMN t_user.enable IS '是否启用';
COMMENT ON COLUMN t_user.create_at IS '创建时间';
COMMENT ON COLUMN t_user.update_at IS '更新时间';

CREATE TABLE t_permission (
    id BIGSERIAL PRIMARY KEY,
    permission_name VARCHAR(100) NOT NULL,
    permission_code VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    resource_type VARCHAR(20) NOT NULL,
    resource_path VARCHAR(255) DEFAULT NULL,
    sort_order INTEGER NOT NULL,
    enable BOOLEAN NOT NULL DEFAULT TRUE,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_permission is '权限表';
COMMENT ON COLUMN t_permission.id IS '主键';
COMMENT ON COLUMN t_permission.permission_name IS '权限名称（如“⽤⼾管理”、“新增⽤⼾”）';
COMMENT ON COLUMN t_permission.permission_code IS '权限唯⼀标识码（如 user:read , user:create ），⽤于前后端鉴权';
COMMENT ON COLUMN t_permission.parent_id IS '⽗级权限ID（外键⾃关联），为 NULL 时表⽰顶级权限';
COMMENT ON COLUMN t_permission.resource_type IS '资源类型（如 menu 菜单, button 按钮, api 接⼝）';
COMMENT ON COLUMN t_permission.resource_path IS '资源路径（如前端路由 /user/list 或后端接⼝/api/v1/users ）';
COMMENT ON COLUMN t_permission.sort_order IS '顺序，⽤于前端菜单或权限列表的展⽰顺序';
COMMENT ON COLUMN t_permission.enable IS '是否启用';
COMMENT ON COLUMN t_permission.create_at IS '创建时间';
COMMENT ON COLUMN t_permission.update_at IS '更新时间';

CREATE TABLE t_role (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL,
    role_code VARCHAR(100) NOT NULL,
    description TEXT DEFAULT NULL,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_role is '角色表';
COMMENT ON COLUMN t_role.id IS '主键';
COMMENT ON COLUMN t_role.role_name IS '⻆⾊名称（如“系统管理员”、“运维⼈员”、“只读访客”）';
COMMENT ON COLUMN t_role.role_code IS '⻆⾊唯⼀标识码（如 admin , operator ），⽤于程序逻辑判断';
COMMENT ON COLUMN t_role.description IS '⻆⾊描述信息';
COMMENT ON COLUMN t_role.create_at IS '创建时间';
COMMENT ON COLUMN t_role.update_at IS '更新时间';

CREATE TABLE t_role_permission (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_role_permission is '⻆⾊权限关联表';
COMMENT ON COLUMN t_role_permission.id IS '主键';
COMMENT ON COLUMN t_role_permission.role_id IS '⻆⾊ID（外键，关联 t_role.id）';
COMMENT ON COLUMN t_role_permission.permission_id IS '权限ID（外键，关联 t_permission.id）';
COMMENT ON COLUMN t_role_permission.create_at IS '创建时间';
COMMENT ON COLUMN t_role_permission.update_at IS '更新时间';

CREATE TABLE t_user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_user_role is '⽤⼾⻆⾊关联表';
COMMENT ON COLUMN t_user_role.id IS '主键';
COMMENT ON COLUMN t_user_role.user_id IS '⻆⽤⼾ID（外键，关联 t_user.id）';
COMMENT ON COLUMN t_user_role.role_id IS '⻆⾊ID（外键，关联 t_role.id）';
COMMENT ON COLUMN t_user_role.create_at IS '创建时间';
COMMENT ON COLUMN t_user_role.update_at IS '更新时间';

CREATE TABLE t_event_definition (
    id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    format VARCHAR(32) NOT NULL,
    payload_schema TEXT NOT NULL,
    version_number INT NOT NULL,
    description TEXT DEFAULT NULL,
    tags JSONB DEFAULT NULL,
    creator_user_id BIGINT NOT NULL,
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_event_definition is '事件定义表';
COMMENT ON COLUMN t_event_definition.id IS '主键';
COMMENT ON COLUMN t_event_definition.event_type IS '事件类型 ，全局唯⼀，如 order.created';
COMMENT ON COLUMN t_event_definition.name IS '事件名称';
COMMENT ON COLUMN t_event_definition.format IS '消息格式，⽬前统⼀为 JSON';
COMMENT ON COLUMN t_event_definition.payload_schema IS '仅针对 payload 字段的 Schema 定义';
COMMENT ON COLUMN t_event_definition.version_number IS '当前版本号，按照修改次数从1递增';
COMMENT ON COLUMN t_event_definition.description IS '事件描述';
COMMENT ON COLUMN t_event_definition.tags IS '标签列表';
COMMENT ON COLUMN t_event_definition.creator_user_id IS '创建者⽤⼾ID (外键)';
COMMENT ON COLUMN t_event_definition.is_public IS '是否为公共事件';
COMMENT ON COLUMN t_event_definition.create_at IS '创建时间';
COMMENT ON COLUMN t_event_definition.update_at IS '更新时间';

CREATE TABLE t_event_version (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL,
    version_number INT NOT NULL,
    payload_schema TEXT NOT NULL,
    change_log TEXT DEFAULT NULL,
    create_at TIMESTAMP DEFAULT NOW(),
    changed_by BIGINT NOT NULL,
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_event_version is '事件版本表';
COMMENT ON COLUMN t_event_version.id IS '主键';
COMMENT ON COLUMN t_event_version.event_id IS '关联的事件定义ID (外键)';
COMMENT ON COLUMN t_event_version.version_number IS '版本号';
COMMENT ON COLUMN t_event_version.payload_schema IS '该版本下 payload 的 Schema 内容';
COMMENT ON COLUMN t_event_version.change_log IS '变更摘要';
COMMENT ON COLUMN t_event_version.create_at IS '创建时间';
COMMENT ON COLUMN t_event_version.changed_by IS '修改⼈⽤⼾ID';
COMMENT ON COLUMN t_event_version.update_at IS '更新时间';

CREATE TABLE t_client_credential (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    role_name VARCHAR(32) NOT NULL,
    user_id BIGINT NOT NULL,
    jwt_token TEXT NOT NULL,
    status VARCHAR(16) NOT NULL,
    expires_at TIMESTAMP DEFAULT NULL,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_client_credential is '客⼾端凭证表';
COMMENT ON COLUMN t_client_credential.id IS '主键';
COMMENT ON COLUMN t_client_credential.name IS '客⼾端名称';
COMMENT ON COLUMN t_client_credential.role_name IS '对应 Pulsar ⻆⾊名 (同⽤⼾下唯⼀)';
COMMENT ON COLUMN t_client_credential.user_id IS '所属⽤⼾ID (外键)';
COMMENT ON COLUMN t_client_credential.jwt_token IS 'JWT 访问凭证';
COMMENT ON COLUMN t_client_credential.status IS '状态 (ENABLED/DISABLED)';
COMMENT ON COLUMN t_client_credential.expires_at IS '凭证过期时间';
COMMENT ON COLUMN t_client_credential.create_at IS '创建时间';
COMMENT ON COLUMN t_client_credential.update_at IS '更新时间';


CREATE TABLE t_audit_log (
    id BIGSERIAL PRIMARY KEY,
    operator_id VARCHAR(64) NOT NULL,
    operation_type VARCHAR(64) NOT NULL,
    target_resource VARCHAR(256) NOT NULL,
    details JSONB DEFAULT NULL,
    params TEXt DEFAULT NULL,
    source_ip VARCHAR(256) NOT NULL,
    create_at TIMESTAMP DEFAULT NOW()
);
comment on table t_audit_log is '审计⽇志表';
COMMENT ON COLUMN t_audit_log.id IS '主键';
COMMENT ON COLUMN t_audit_log.operator_id IS '操作⼈ID（SSO ID）';
COMMENT ON COLUMN t_audit_log.operation_type IS '操作类型 (如 CREATE_TOPIC，DELETE_EVENT)';
COMMENT ON COLUMN t_audit_log.target_resource IS '操作⽬标资源 (如topic://public/default/order)';
COMMENT ON COLUMN t_audit_log.details IS '操作详情 (变更前/后内容等)';
COMMENT ON COLUMN t_audit_log.params IS '请求参数';
COMMENT ON COLUMN t_audit_log.source_ip IS '操作来源IP';
COMMENT ON COLUMN t_audit_log.create_at IS '操作时间';

CREATE TABLE t_tenant (
    id BIGSERIAL PRIMARY KEY,
    tenant_code VARCHAR(100) NOT NULL UNIQUE,
    tenant_name VARCHAR(200) NOT NULL UNIQUE,
    description TEXT,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    create_at TIMESTAMP DEFAULT NOW(),
    update_at TIMESTAMP DEFAULT NOW()
);
comment on table t_tenant is '租户管理表';
COMMENT ON COLUMN t_tenant.id IS '主键';
COMMENT ON COLUMN t_tenant.tenant_code IS '租户编码，全局唯一，如：zevent';
COMMENT ON COLUMN t_tenant.tenant_name IS '租户名称，如：Pulsar默认租户';
COMMENT ON COLUMN t_tenant.description IS '租户描述';
COMMENT ON COLUMN t_tenant.is_default IS '是否默认租户，TRUE-是、FALSE-否';
COMMENT ON COLUMN t_tenant.is_active IS '是否启用租户，TRUE-是、FALSE-否';
COMMENT ON COLUMN t_tenant.create_at IS '创建时间';
COMMENT ON COLUMN t_tenant.update_at IS '更新时间';