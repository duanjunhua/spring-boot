package com.duanjh.runner;

import com.duanjh.config.DynamicDataSource;
import com.duanjh.entity.DataSourceEntity;
import com.duanjh.mapper.DataSourceEntityMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-19 周二 10:37
 * @Version: v1.0
 * @Description: 加载全部数据源
 */
@Slf4j
@Component
public class LoadDataSourceRunner implements CommandLineRunner {

    @Resource
    DynamicDataSource dynamicDataSourceConfig;

    @Autowired
    DataSourceEntityMapper dsMapper;

    @Override
    public void run(String... args) throws Exception {

        // 加载所有数据源
        List<DataSourceEntity> dynamics = dsMapper.selectList(null);

        if(CollectionUtils.isEmpty(dynamics)) {
            log.warn("动态数据源为空");
            return;
        }

        dynamicDataSourceConfig.createDataSource(dynamics);
        log.info("已加载全部数据源");
    }
}
