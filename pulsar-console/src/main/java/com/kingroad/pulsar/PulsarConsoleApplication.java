package com.kingroad.pulsar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:46
 * @Version: v1.0
 * @Description: Apache Pulsar管理控制台
 */
@SpringBootApplication
public class PulsarConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PulsarConsoleApplication.class, args);
    }

}