package com.duanjh.mongo.multisource.primary.singlesource;

import org.bson.types.ObjectId;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 15:08
 * @Version: v1.0
 * @Description: 方式二:自定义Service
 */
public interface MongoUserService {

    MongoUser saveUser(MongoUser mongoUser);

    MongoUser findByUsername(String username);

    Long updateUser(MongoUser mongoUser);

    void deleteUser(ObjectId id);

}
