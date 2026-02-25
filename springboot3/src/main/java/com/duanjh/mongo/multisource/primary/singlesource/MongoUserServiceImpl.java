package com.duanjh.mongo.multisource.primary.singlesource;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 15:10
 * @Version: v1.0
 * @Description: 方式二:自定义Mongo持久化实现
 */
@Component
public class MongoUserServiceImpl implements MongoUserService{

    @Autowired
    MongoTemplate template;

    /**
     * 保存用户
     */
    @Override
    public MongoUser saveUser(MongoUser mongoUser) {
        return template.save(mongoUser);
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public MongoUser findByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return template.findOne(query, MongoUser.class);
    }

    /**
     * 更新用户
     */
    @Override
    public Long updateUser(MongoUser mongoUser) {
        Query query = new Query(Criteria.where("id").is(mongoUser.getId()));
        Update update = new Update().set("username", mongoUser.getUsername()).set("password", mongoUser.getPassword()).set("email", mongoUser.getEmail());

        //更新查询返回结果集的第一条
        UpdateResult result = template.updateFirst(query, update, MongoUser.class);

        return ObjectUtils.isEmpty(result) ? 0 : result.getMatchedCount();
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void deleteUser(ObjectId id) {
        Query query = new Query(Criteria.where("id").is(id));
        template.remove(query, MongoUser.class);
    }
}
