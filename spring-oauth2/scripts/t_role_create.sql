CREATE TABLE t_role (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    sn varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='角色';