package com.kingroad.pulsar.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 11:23
 * @Version: v1.0
 * @Description: 封装方法操作前后的内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {

    /**
     * 变更前内容
     */
    private String beforeChange;

    /**
     * 变更后内容
     */
    private String afterChange;

}
