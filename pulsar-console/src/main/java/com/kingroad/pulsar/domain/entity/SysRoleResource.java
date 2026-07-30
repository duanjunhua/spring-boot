package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:03
 * @Version: v1.0
 * @Description: ⻆⾊权限
 */
@Data
@Entity
@Table(name = "t_role_permission")
public class SysRoleResource extends BaseAuditEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ⻆⾊ID（外键，关联 t_role.id）
     */
    private Long roleId;

    /**
     * 权限ID（外键，关联 t_permission.id）
     */
    @Column(name = "permission_id")
    private Long resourceId;


}
