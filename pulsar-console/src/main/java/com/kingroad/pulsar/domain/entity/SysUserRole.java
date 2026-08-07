package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:05
 * @Version: v1.0
 * @Description: ⽤⼾⻆⾊
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user_role")
public class SysUserRole extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ⻆⽤⼾ID（外键，关联 t_user.id）
     */
    private Long userId;

    /**
     * ⻆⾊ID（外键，关联 t_role.id）
     */
    private Long roleId;

    public SysUserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
