package com.kingroad.pulsar.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:22
 * @Version: v1.0
 * @Description: 客⼾端凭证
 */
@Data
@Entity
@Table(name = "t_client_credential")
public class ClientCredential extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客⼾端名称
     */
    @Column(name = "name")
    private String clientName;

    /**
     * 对应 Pulsar ⻆⾊名 (同⽤⼾下唯⼀)
     */
    private String roleName;

    /**
     * 所属⽤⼾ID (外键)
     */
    private Long userId;

    /**
     * JWT 访问凭证
     */
    private String jwtToken;

    /**
     * 状态 (ENABLED/DISABLED)
     */
    private String status = Status.ENABLED.name();

    /**
     * 凭证过期时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiresAt;

    public enum Status{
        ENABLED, DISABLED
    }
}
