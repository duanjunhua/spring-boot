package com.duanjh.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-10-10
 * @Version: V1.0
 * @Description:
 */
@RestController
@RefreshScope
public class IndexController {

    @Value("${nacos.config.val}")
    private String nacosConfigVal;

    @GetMapping("/")
    public String index(){
        return nacosConfigVal;
    }
}
