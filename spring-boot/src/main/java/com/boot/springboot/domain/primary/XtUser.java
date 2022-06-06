package com.boot.springboot.domain.primary;

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
@Entity(name = "xt_user")
public class XtUser {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "login_name")
    private String loginName;

}
