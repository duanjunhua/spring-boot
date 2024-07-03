package com.duanjh.module;

import cn.shuibo.annotation.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync    //开启异步操作支持
@EnableSecurity //开启接口RSA加密
@SpringBootApplication
public class SpringbootModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootModuleApplication.class, args);
    }

}
