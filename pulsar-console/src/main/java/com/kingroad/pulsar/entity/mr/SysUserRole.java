package com.kingroad.pulsar.entity.mr;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:49
 * @Version: v1.0
 * @Description: ⽤⼾⻆⾊关联
 */
@Data
@Builder
@TableName("t_user_role")
public class SysUserRole {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * ⻆⽤⼾ID（外键，关联 t_user.id）
     */
    private Long userId;

    /**
     * ⻆⾊ID（外键，关联 t_role.id）
     */
    private Long roleId;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

}
