CREATE TABLE t_login (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    username varchar(255) DEFAULT NULL COMMENT '员工用户名',
    password varchar(255) DEFAULT NULL COMMENT '密码',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='用户登录';