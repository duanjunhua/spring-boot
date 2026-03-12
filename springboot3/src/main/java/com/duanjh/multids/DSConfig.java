package com.duanjh.multids;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-09 周一 15:16
 * @Version: v1.0
 * @Description: 多数据源配置
 */
@Configuration
public class DSConfig {

    /**
     * 配置数据源，设置一个数据源设置为@Primary，默认主数据源
     */
    @Primary
    @Bean(name = "primaryDs")
    @ConfigurationProperties(prefix = "app.datasource.primary") // 对应配置文件app.datasource.primary.*
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "secondaryDs")
    @ConfigurationProperties(prefix = "app.datasource.secondary")   // 对应配置文件app.datasource.secondary.*
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

}
