package com.kingroad.pulsar.domain.vo.broker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.pulsar.common.policies.data.NamespaceOwnershipStatus;

import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-03 周一 14:42
 * @Version: v1.0
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerVo {

    /**
     * 节点地址
     */
    private String brokerAddr;

    /**
     * CPU使用率 0~1
     */
    private double cpuUsage;

    /**
     * JVM堆内存使用率
     */
    private double memoryUsage;

    /**
     * 入站带宽 MB/s
     */
    private double bandwidthIn;

    /**
     * 出站带宽 MB/s
     */
    private double bandwidthOut;

    /**
     * 在线生产者总数
     */
    private int producerCount;

    /**
     * 在线消费者总数
     */
    private int consumerCount;

    /**
     * 托管Topic总数
     */
    private long totalTopics;

    /**
     * 每秒消息入吞吐量
     */
    private double msgInRate;

    /**
     * 节点承载的命名空间列表
     */
    private Map<String, NamespaceOwnershipStatus> ownedNamespaces;

}
