package com.duanjh.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class OauthAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthAppApplication.class, args);
    }

}
