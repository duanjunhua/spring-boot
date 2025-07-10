CREATE TABLE t_permission (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    resource varchar(255) NOT NULL,
    state int(11) DEFAULT NULL,
    menu_id bigint(20) DEFAULT NULL,
    expression varchar(255) NOT NULL,
    PRIMARY KEY (id),
    KEY menu_id (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='权限';;