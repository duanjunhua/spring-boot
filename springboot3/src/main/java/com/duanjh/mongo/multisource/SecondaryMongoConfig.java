package com.duanjh.mongo.multisource;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 16:01
 * @Version: v1.0
 * @Description:
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.duanjh.mongo.multisource.secondary", mongoTemplateRef = SecondaryMongoConfig.MONGO_TEMPLATE)
public class SecondaryMongoConfig {

    public static final String MONGO_TEMPLATE = "secondaryMongoTemplate";

}
