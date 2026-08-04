package com.kingroad.pulsar.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-03 周一 16:29
 * @Version: v1.0
 * @Description: 消费 / 积压相关
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionBacklog {

    /**
     * 消息读取速率（消费拉取）
     */
    @JsonProperty("brk_ml_ReadEntriesRate")
    private String readEntriesRate;

    /**
     * 消息读取流量 bytes/s
     */
    @JsonProperty("brk_ml_ReadEntriesBytesRate")
    private String readEntriesBytesRate;

    /**
     * 读取成功次数速率
     */
    @JsonProperty("brk_ml_ReadEntriesSucceeded")
    private String readEntriesSucceeded;

    /**
     * 读取失败速率
     */
    @JsonProperty("brk_ml_ReadEntriesErrors")
    private String readEntriesErrors;

    /**
     * 读消息缓存未命中速率
     */
    @JsonProperty("brk_ml_ReadEntriesOpsCacheMissesRate")
    private String readEntriesOpsCacheMissesRate;

    /**
     * MarkDelete 速率（消费位点确认，游标推进）
     */
    @JsonProperty("brk_ml_MarkDeleteRate")
    private String markDeleteRate;

    /**
     * 未消费消息堆积条数
     */
    @JsonProperty("brk_ml_NumberOfMessagesInBacklog")
    private Long numberOfMessagesInBacklog;

    /**
     * 存储消息总字节大小
     */
    @JsonProperty("brk_ml_StoredMessagesSize")
    private String storedMessagesSize;
}
