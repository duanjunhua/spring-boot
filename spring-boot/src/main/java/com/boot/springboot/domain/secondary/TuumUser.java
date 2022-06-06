package com.boot.springboot.domain.secondary;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description:
 */
@Data
@Entity(name = "t_uum_user")
public class TuumUser {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_login_name")
    private String userLoginName;

//    @Column(name = "user_password")
//    private String userPassword;

}
