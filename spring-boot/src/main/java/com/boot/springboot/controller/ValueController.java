package com.boot.springboot.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-30
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@Data
@RestController
public class ValueController {

    @Value("${user.random.value}")
    private String randomVal;

    @Value("${server.port}")
    private String port;

    @GetMapping("/val")
    public void val(){
        log.info("Server Running on port: {}, random value: {}", port, randomVal);
    }

}
