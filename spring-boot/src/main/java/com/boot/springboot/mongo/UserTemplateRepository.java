package com.boot.springboot.mongo;

import com.boot.springboot.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description: MongoDB持久化
 */
public interface UserTemplateRepository extends MongoRepository<User, Long> {

    User findByUserName(String userName);
}
