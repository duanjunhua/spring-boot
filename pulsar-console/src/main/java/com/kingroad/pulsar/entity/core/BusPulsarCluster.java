package com.kingroad.pulsar.entity.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kingroad.pulsar.constant.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 10:37
 * @Version: v1.0
 * @Description: Pulsar集群
 */
@Data
@TableName("t_pulsar_cluster")
public class BusPulsarCluster {

    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 集群名称 (如 prod-cluster , staging-cluster )
     */
    private String name;

    /**
     * Pulsar 服务 URL (如 pulsar://pulsar.example.com:6650 )
     */
    private String serviceUrl;

    /**
     * Pulsar Admin API URL (如 http://pulsar-admin.example.com:8080 )
     */
    private String adminApiUrl;

    /**
     * 认证插件类名 (如org.apache.pulsar.client.impl.auth.AuthenticationToken）
     */
    private String authPlugin;

    /**
     * 认证参数 (如 JWT token 或 JSON 格式的密钥信息)
     */
    private String authParams;

    /**
     * 是否为默认集群
     */
    private Boolean isDefault = Boolean.FALSE;

    /**
     * 集群状态 (ACTIVE/INACTIVE)
     */
    private String status = StatusEnum.ACTIVE.name();

    /**
     * 集群描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

}
