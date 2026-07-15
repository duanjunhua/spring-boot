package com.kingroad.pulsar;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:46
 * @Version: v1.0
 * @Description: Apache Pulsar管理控制台
 */

@MapperScan("com.kingroad.pulsar.mapper")
@SpringBootApplication
public class PulsarConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PulsarConsoleApplication.class, args);
    }

    /**
     * MyBatis分页
     */
    @Bean
    public MybatisPlusInterceptor interceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        return interceptor;
    }
}
