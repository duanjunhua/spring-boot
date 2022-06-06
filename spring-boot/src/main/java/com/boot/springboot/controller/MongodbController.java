package com.boot.springboot.controller;

import com.boot.springboot.domain.User;
import com.boot.springboot.mongo.UserTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description: MongoDB整合
 */
@RestController
public class MongodbController {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserTemplateRepository userTemplateRepository;

    @GetMapping("/mongo")
    public User updateMongoData(){
        //不给顶集合名称时，自动设置集合名称为类名
        //mongoTemplate.insert(new User(1L, "Nono Huang"));
        User u = mongoTemplate.findOne(new Query(new Criteria().andOperator(Criteria.where("id").is(1L))), User.class);
        return u;
    }

    @GetMapping("/mongo/repo")
    public User mongoUser(){
        User u = userTemplateRepository.findByUserName("Nono Huang");
        return u;
    }
}
