package com.duanjh.mongo.multisource.secondary;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 16:12
 * @Version: v1.0
 * @Description: 实体对象
 */
@Data
@Document(value = "users")
@NoArgsConstructor
@AllArgsConstructor
public class SecondaryUser implements Serializable {

    @Id // Mongo自定义ID
    private ObjectId id;

    private String username;

    private String password;

    private String email;

    private String job;

    public SecondaryUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
