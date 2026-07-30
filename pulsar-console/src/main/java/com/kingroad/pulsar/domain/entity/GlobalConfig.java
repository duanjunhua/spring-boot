package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:26
 * @Version: v1.0
 * @Description: 全局配置
 */
@Data
@Entity
@Table(name = "t_global_config")
public class GlobalConfig extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 配置项键名，全局唯⼀ （如：sso.enable：是否开启sso， 0-关闭 1-开启、init_admin_flag：是否已完成初始化引导 0-未初始化 1-已初始化等）
     */
    private String configKey;

    /**
     * 配置项值 (JSON 格式存储复杂对象)
     */
    private String configValue;

    /**
     * 配置项描述
     */
    private String description;

}
