package com.kingroad.pulsar.domain.entity;

import com.kingroad.pulsar.authorization.sso.SsoConst;
import com.kingroad.pulsar.common.CommonConst;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:43
 * @Version: v1.0
 * @Description: 系统用户
 */
@Data
@Entity
@Table(name = "t_user")
public class SysUser extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 配SSO 平台的唯⼀⽤⼾ID
     */
    @Column(name = "user_id")
    private String ssoId;

    /**
     * ⽤⼾显⽰名
     */
    private String username;

    /**
     * ⽤⼾中文名
     */
    private String chineseName;

    /**
     * 对应 Pulsar 租⼾名，全局唯⼀
     */
    private String tenantName;

    /**
     * 关联的 Pulsar 集群ID (外键)
     */
    @Column(name = "pulsar_cluster_id")
    private Long clusterId;

    /**
     * 是否为超级管理员，TRUE: 超级管理员 FALSE: 普通用户
     */
    private Boolean isSuperAdmin = Boolean.FALSE;

    /**
     * 仅超级管理员使⽤，MD5 加密后的密码摘要
     */
    private String passwordHash;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最近登录时间
     */
    @Column(name = "last_login_time")
    private LocalDateTime lastLogin;

    /**
     * 是否启用
     */
    private Boolean enable =  Boolean.TRUE;

    /**
     * 用户角色集合
     */
    @Transient
    private List<SysRole> roleList;

    /**
     * 用户资源集合
     */
    @Transient
    private List<SysResource> resList;

    public static SysUser buildSsoUser(String authSsoId, String nickname, String email) {
        SysUser u = new SysUser();
        u.setSsoId(authSsoId);
        u.setUsername(SsoConst.SSO_PREFIX + authSsoId);
        u.setChineseName(nickname);
        u.setEmail(email);
        u.setEnable(true);
        u.setPasswordHash("");
        u.setTenantName(CommonConst.DEFAULT_TENANT);
        u.setClusterId(CommonConst.DEFAULT_CLUSTER_ID);
        return u;
    }

}
