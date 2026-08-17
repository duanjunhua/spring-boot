package com.kingroad.pulsar.domain.entity;

import com.kingroad.pulsar.domain.vo.bookkeeper.BkNodeVo;
import com.kingroad.pulsar.domain.vo.broker.BrokerVo;
import com.kingroad.pulsar.domain.vo.zookeeper.ZkNodeVo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:35
 * @Version: v1.0
 * @Description: Pulsar集群
 */
@Data
@Entity
@Table(name = "t_pulsar_cluster")
public class PulsarCluster extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 集群名称 (如 prod-cluster , staging-cluster )
     */
    @Column(name = "name")
    private String clusterName;

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
    private String status = Status.ACTIVE.name();

    /**
     * 集群描述
     */
    private String description;

    /**
     * 集群版本
     */
    @Transient
    private String clusterVersion;

    /**
     * Broker节点
     */
    @Transient
    private List<BrokerVo> brokers;
    /**
     * BookKeeper节点
     */
    private List<BkNodeVo> bkNodes;

    /**
     * ZooKeeper节点
     */
    private List<ZkNodeVo> zkNodes;

    /**
     * 集群状态
     */
    public enum Status{
        ACTIVE, INACTIVE, UNKNOWN
    }

}
