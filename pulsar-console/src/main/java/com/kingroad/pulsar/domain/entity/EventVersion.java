package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:17
 * @Version: v1.0
 * @Description: 事件版本
 */
@Data
@Entity
@Table(name = "t_event_version")
@EntityListeners(AuditingEntityListener.class)
public class EventVersion extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联的事件定义ID (外键)
     */
    private Long eventId;

    /**
     * 事件版本号
     */
    private Integer versionNumber;

    /**
     * 该版本下 payload 的 Schema 内容
     */
    private String payloadSchema;

    /**
     * 变更摘要
     */
    private String changeLog;

    /**
     * 修改⼈⽤⼾ID
     */
    @LastModifiedBy
    private Long updateBy;
}
