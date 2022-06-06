package com.boot.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Mybatis 配置
@MapperScan("com.boot.springboot.dao.mybatis")
// 主程序
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);

        //禁止使用命令行参数
        app.setAddCommandLineProperties(false);

        //启动项目
        app.run(args);
    }
}
