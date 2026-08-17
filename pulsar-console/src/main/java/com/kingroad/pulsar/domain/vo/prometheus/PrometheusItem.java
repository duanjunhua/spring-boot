package com.kingroad.pulsar.domain.vo.prometheus;

import lombok.Data;

import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-13 周四 17:13
 * @Version: v1.0
 * @Description: Prometheus监控指标
 */
@Data
public class PrometheusItem {

    private Map<String, Object> metrics;

    private Map<String, String> dimensions;

}
