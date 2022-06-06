package com.boot.springboot.config.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-06
 * @Version: V1.0
 * @Description: 自定义检测器
 */
@Slf4j
@Component
public class RocketMQHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check();
        if(0 != errorCode){
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        //withDetail 添加描述信息
        return Health.up().withDetail("version", "1.0.0").withDetail("description", "Test Personal Health").build();
    }

    private int check(){
        // 对监控对象的检测操作
        log.info("Health Indicator");
        return 0;
    }
}
