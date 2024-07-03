package com.duanjh.config;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.impl.InfluxDBImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-08-16
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@Configuration
public class InfluxDbConfig {

    @Value("${spring.influx.url}")
    private String influxDbUrl;

    @Value("${spring.influx.database}")
    private String database;

    @Value("${spring.influx.retentionPolicy}")
    private String policy;

    @Value("${thread.maxConnection}")
    private String maxConnection;


}
