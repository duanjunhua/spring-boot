package com.kingroad.pulsar.domain.vo.broker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-03 周一 16:14
 * @Version: v1.0
 * @Description: ManagedLedger核心指标
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerCacheIndicator {

    /**
     * 当前缓存内有效条目数量
     */
    @JsonProperty("brk_ml_cache_entries")   //对应Pulsar Broker metrics指标字段
    private Long entries;

    /**
     * 生命周期内总共放入缓存的消息条目总数
     */
    @JsonProperty("brk_ml_cache_inserted_entries_total")
    private Long insertedEntriesTotal;

    /**
     * 缓存淘汰出去的条目累计总数
     */
    @JsonProperty("brk_ml_cache_evicted_entries_total")
    private Long evictedEntriesTotal;

    /**
     * 当前周期缓存触发淘汰次数（驱逐动作次数）
     */
    @JsonProperty("brk_ml_cache_evictions")
    private Long evictions;

    /**
     * 缓存命中率（每秒命中次数）
     */
    @JsonProperty("brk_ml_cache_hits_rate")
    private Double hitsRate;

    /**
     * 缓存命中读取流量（bytes/s）
     */
    @JsonProperty("brk_ml_cache_hits_throughput")
    private Double hitsThroughput;

    /**
     * 缓存未命中速率
     */
    @JsonProperty("brk_ml_cache_misses_rate")
    private Double missesRate;

    /**
     * 缓存未命中时读取流量
     */
    @JsonProperty("brk_ml_cache_misses_throughput")
    private Double missesThroughput;

    /**
     * 缓存占用内存字节数
     */
    @JsonProperty("brk_ml_cache_used_size")
    private Long usedSize;

    /**
     * ML缓存池已申请内存
     */
    @JsonProperty("brk_ml_cache_pool_allocated")
    private Double allocated;

    /**
     * ML 缓存池实际使用内存
     */
    @JsonProperty("brk_ml_cache_pool_used")
    private Double used;

    /**
     * 缓存池活跃分配对象总数
     */
    @JsonProperty("brk_ml_cache_pool_active_allocations")
    private Long activeAllocations;

    /**
     * 区分大小对象活跃分配数（内存池分级）
     */
    @JsonProperty("brk_ml_cache_pool_active_allocations_small")
    private Long activeAllocationsSmall;
    @JsonProperty("brk_ml_cache_pool_active_allocations_normal")
    private Long activeAllocationsNormal;
    @JsonProperty("brk_ml_cache_pool_active_allocations_huge")
    private Long activeAllocationsHuge;

    /**
     * 当前托管账本（ManagedLedger）实例数量
     */
    @JsonProperty("brk_ml_count")
    private Long count;

}
