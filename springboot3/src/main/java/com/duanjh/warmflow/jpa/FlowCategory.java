package com.duanjh.warmflow.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.warm.flow.core.dto.Tree;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-20 周四 10:38
 * @Version: v1.0
 * @Description:
 */
@Data
@Entity     // name属性是实体名称，默认为实体类的非限定名称，用于引用查询中的实体
@Table(name = "flow_category")    // name：表名，默认为实体名称， schema：表的schema,默认为用户的默认schema
@EntityListeners(AuditingEntityListener.class)      // 注册实体监听器，用于自动填充审计字段
public class FlowCategory {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * 子
     */
    @Transient
    private List<FlowCategory> children = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)    // 标注只在创建时设置
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

}
