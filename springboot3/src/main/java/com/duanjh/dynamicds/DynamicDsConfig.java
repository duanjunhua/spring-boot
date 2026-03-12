package com.duanjh.dynamicds;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-10 周二 17:36
 * @Version: v1.0
 * @Description: 动态数据源配置
 */
@Configuration
public class DynamicDsConfig {

    //创建第一个主库数据源
    @Bean(name = "primaryDynamicDs")
    @ConfigurationProperties(prefix = "app.dynamicds.primary")
    public DataSource primaryDynamicDs() {
        return DataSourceBuilder.create().build();
    }
    //创建第二个从库数据源
    @Bean(name = "secondaryDynamicDs")
    @ConfigurationProperties(prefix = "app.dynamicds.second")
    public DataSource secondaryDynamicDs() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDs")   //动态数据源，实际使用的数据源时数据源路由根据key 选择的。默认数据源为第一个数据源(主库)
    // @Primary    //主数据源，使用的是此数据源
    public DynamicDs dataSource(DataSource primaryDynamicDs, DataSource secondaryDynamicDs) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DsType.PRIMARY, primaryDynamicDs);
        targetDataSources.put(DsType.SECONDARY, secondaryDynamicDs);
        //默认返回的也是一个datasource
        return new DynamicDs(primaryDynamicDs, targetDataSources);
    }
}
