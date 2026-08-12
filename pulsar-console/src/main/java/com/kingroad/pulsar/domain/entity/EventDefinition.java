package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:07
 * @Version: v1.0
 * @Description: 事件定义
 */
@Data
@Entity
@Table(name = "t_event_definition")
public class EventDefinition extends BaseAuditEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 事件类型 ，全局唯⼀，如 order.created
     */
    private String eventType;

    /**
     * 事件名称
     */
    @Column(name = "name")
    private String eventName;

    /**
     * 消息格式，⽬前统⼀为JSON
     */
    @Column(name = "format")
    private String messageFormat;

    /**
     * 仅针对 payload 字段的 Schema 定义
     */
    private String payloadSchema;

    /**
     * 当前版本号，按照修改次数从1递增
     */
    @Version
    private Integer versionNumber;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 是否为公共事件
     */
    private Boolean isPublic = Boolean.FALSE;

    /**
     * 创建者⽤⼾ID (外键)
     */
    private Long creatorUserId;

}
