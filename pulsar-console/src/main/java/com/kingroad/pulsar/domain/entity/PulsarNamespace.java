package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 09:11
 * @Version: v1.0
 * @Description: 业务系统（对应Pulsar命名空间）
 */
@Data
@Entity
@Table(name = "t_pulsar_namespace")
public class PulsarNamespace {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 命名空间编码（业务系统编码）
     */
    private String spaceCode;

    /**
     * 命名空间名称
     */
    @Column(name = "space_name")
    private String space_name;



}
