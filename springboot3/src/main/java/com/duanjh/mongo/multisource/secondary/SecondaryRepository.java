package com.duanjh.mongo.multisource.secondary;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 16:18
 * @Version: v1.0
 * @Description: 多数据源
 */
@Repository
public interface SecondaryRepository extends MongoRepository<SecondaryUser, ObjectId> {

}
