package com.duanjh.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-17 周二 17:10
 * @Version: v1.0
 * @Description: 聊天会话
 */
@Data
@Entity(name = "chat_session")
@EntityListeners(AuditingEntityListener.class)
public class ChatSession implements Serializable {

    /**
     * 会话ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 会话标题
     */
    private String sessionTitle;

    /**
     * 状态: active, archived, deleted
     */
    private String status;

    /**
     * 消息数量
     */
    private Integer messageCount;

    @UpdateTimestamp
    private LocalDateTime lastActiveTime;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

}
