package com.duanjh;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableJpaAuditing(auditorAwareRef = "jpaUserAuditor")  // 开启JPA审计，使得@CreateDate、@UpdateTimestamp生效，若要@CreateBy、@LastModifiedBy生效需实现AuditorAware并指定auditorAwareRef
@EnableScheduling   // 开启定时任务
@SpringBootApplication
@MapperScan(basePackages = {"com.duanjh.mybatisplus.mapper", "com.duanjh.shiro"})    // 使用MybatisPlus需要标注Mapper接口位置
public class Springboot3Application extends SpringBootServletInitializer {  // Thymeleaf需要继承SpringBootServletInitializer添加 Servlet 的支持

    public static void main(String[] args) {
        log.info("Application to be start");
        SpringApplication.run(Springboot3Application.class, args);
        log.info("Application started");
    }

    /**
     * Thymeleaf增删改查添加 Servlet 的支持重写方法
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Springboot3Application.class);
    }
}
