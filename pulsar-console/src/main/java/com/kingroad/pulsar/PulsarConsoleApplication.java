package com.kingroad.pulsar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:46
 * @Version: v1.0
 * @Description: Apache Pulsar管理控制台
 */
@EnableScheduling   // 启用定时任务
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")    // 开启JPA审计，使得@CreateDate、@UpdateTimestamp生效，并指定@CreateBy、@LastModifiedBy生成的ID
@SpringBootApplication
public class PulsarConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PulsarConsoleApplication.class, args);
    }

}