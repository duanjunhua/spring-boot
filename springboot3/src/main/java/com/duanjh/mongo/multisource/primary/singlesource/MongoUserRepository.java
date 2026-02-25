package com.duanjh.mongo.multisource.primary.singlesource;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 14:20
 * @Version: v1.0
 * @Description: 方式一:使用MongoRepository
 */
@Repository
public interface MongoUserRepository extends MongoRepository<MongoUser, ObjectId> {

}
