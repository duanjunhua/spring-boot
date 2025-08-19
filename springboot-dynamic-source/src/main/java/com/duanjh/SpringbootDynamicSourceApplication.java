package com.duanjh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.duanjh.**.mapper.**")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringbootDynamicSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDynamicSourceApplication.class, args);
    }

}
