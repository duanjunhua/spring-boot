package com.duanjh.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-19 周二 14:58
 * @Version: v1.0
 * @Description:
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.typt")
    public DataSource typtDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.yhzs")
    public DataSource yhzsDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource createDynamicDataSource(){
        Map<Object,Object> dataSourceMap = new HashMap<>();
        DataSource defaultDataSource = masterDataSource();
        dataSourceMap.put("master", defaultDataSource);
        dataSourceMap.put("typt", typtDataSource());
        dataSourceMap.put("yhzs", yhzsDataSource());
        return new DynamicDataSource(defaultDataSource,dataSourceMap);
    }
}
