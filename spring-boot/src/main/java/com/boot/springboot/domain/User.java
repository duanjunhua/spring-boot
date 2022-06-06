package com.boot.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-30
 * @Version: V1.0
 * @Description:
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String userName;

}
