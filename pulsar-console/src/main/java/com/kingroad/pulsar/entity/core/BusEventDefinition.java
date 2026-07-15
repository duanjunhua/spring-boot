package com.kingroad.pulsar.entity.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kingroad.pulsar.constant.EventFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 14:01
 * @Version: v1.0
 * @Description: 事件定义表
 */
@Data
@TableName("t_event_definition")
public class BusEventDefinition {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 事件类型 ，全局唯⼀，如 order.created
     */
    private String eventType;

    /**
     * 事件名称
     */
    private String name;

    /**
     * 消息格式，⽬前统⼀为 JSON
     */
    private String format = EventFormat.JSON.name();

    /**
     * 仅针对 payload 字段的 Schema 定义
     */
    private String payloadSchema;

    /**
     * 当前版本号，按照修改次数从1递增
     */
    private Integer versionNumber = 1;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 创建者⽤⼾ID (外键)
     */
    private Long creatorUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 是否为公共事件
     */
    private Boolean isPublic = Boolean.FALSE;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;


}
