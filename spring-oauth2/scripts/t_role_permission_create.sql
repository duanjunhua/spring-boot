CREATE TABLE t_role_permission (
    role_id bigint(20) NOT NULL,
    permission_id bigint(20) NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    KEY permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='角色权限关联';;