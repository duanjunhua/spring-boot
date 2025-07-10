CREATE TABLE t_login_role (
    login_id bigint(20) NOT NULL,
    role_id bigint(20) NOT NULL,
    PRIMARY KEY (login_id,role_id),
    KEY role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='用户角色关联';