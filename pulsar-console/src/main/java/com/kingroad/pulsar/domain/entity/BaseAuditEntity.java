package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:13
 * @Version: v1.0
 * @Description:
 */
@Data
@MappedSuperclass // 父类不生成表，字段被子类实体继承
@EntityListeners(AuditingEntityListener.class) // 开启JPA审计监听
public class BaseAuditEntity {

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updateAt;

}
