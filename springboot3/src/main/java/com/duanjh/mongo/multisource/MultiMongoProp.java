package com.duanjh.mongo.multisource;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 15:57
 * @Version: v1.0
 * @Description: Mongo多数据源的配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "multi.mongodb")
public class MultiMongoProp {

    MongoProperties primary = new MongoProperties();

    MongoProperties secondary = new MongoProperties();

}
