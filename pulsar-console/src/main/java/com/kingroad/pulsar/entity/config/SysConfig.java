package com.kingroad.pulsar.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:12
 * @Version: v1.0
 * @Description:
 */
@Data
@TableName("t_global_config")
public class SysConfig {

    @TableId(type = IdType.AUTO)
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

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

}
