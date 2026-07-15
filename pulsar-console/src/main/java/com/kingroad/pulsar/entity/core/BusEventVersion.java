package com.kingroad.pulsar.entity.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kingroad.pulsar.constant.EventFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 14:20
 * @Version: v1.0
 * @Description: 事件版本表
 */
@Data
@TableName("t_event_version")
public class BusEventVersion {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联的事件定义ID (外键)
     */
    private Long eventId;

    /**
     * 变更摘要
     */
    private String changeLog;

    /**
     * 该版本下 payload 的 Schema 内容
     */
    private String payloadSchema;

    /**
     * 版本号
     */
    private Integer versionNumber = 1;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改⼈⽤⼾ID
     */
    private Long changedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

}
