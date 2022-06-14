package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class SpringbootMinioApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMinioApplication.class, args);
    }

}
