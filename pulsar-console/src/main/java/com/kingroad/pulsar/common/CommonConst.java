package com.kingroad.pulsar.common;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-31 周五 10:46
 * @Version: v1.0
 * @Description: 通用常量
 */
public class CommonConst {

    /**
     * 初始化系统配置
     */
    public static final String INIT_SYSTEM = "init_finished";

    public static final String COM_ONE = "1";

    // 默认租户
    public static final String DEFAULT_TENANT = "zevent";

    // 默认集群ID
    public static final Long DEFAULT_CLUSTER_ID = 0L;

    public static final String DEFAULT_PULSAR_ADMIN_AUTH_PLUGIN = "org.apache.pulsar.client.impl.auth.AuthenticationToken";

    /*-------------- 角色编码 ----------------*/
    // 管理员
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    // 普通用户
    public static final String ROLE_USER = "ROLE_USER";
    // 只读用户
    public static final String ROLE_READ = "ROLE_READ";
}
