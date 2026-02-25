package com.duanjh.mongo.multisource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 16:05
 * @Version: v1.0
 * @Description:
 */
@Configuration
public class MultiMongoDSConfig {

    @Autowired
    MultiMongoProp dsProps;

    @Primary    // 必须指定主库
    @Bean(name = PrimaryMongoConfig.MONGO_TEMPLATE)    // name与PrimaryMongoConfig中定义的MONGO_TEMPLATE名保持一致
    public MongoTemplate primaryTemplate(){
        return new MongoTemplate(primaryMongoDbFactory(dsProps.getPrimary()));
    }

    @Primary
    @Bean
    public MongoDatabaseFactory primaryMongoDbFactory(MongoProperties props){
        MongoClient client = MongoClients.create(props.getUri());
        return new SimpleMongoClientDatabaseFactory(client, props.getDatabase());
    }


    @Bean(name = SecondaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate secondaryTemplate(){
        return new MongoTemplate(secondaryMongoDbFactory(dsProps.getSecondary()));
    }

    @Bean
    public MongoDatabaseFactory secondaryMongoDbFactory(MongoProperties props){
        MongoClient client = MongoClients.create(props.getUri());
        return new SimpleMongoClientDatabaseFactory(client, props.getDatabase());
    }

}
