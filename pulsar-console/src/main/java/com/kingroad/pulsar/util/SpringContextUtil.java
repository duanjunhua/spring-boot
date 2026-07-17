package com.kingroad.pulsar.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 13:39
 * @Version: v1.0
 * @Description: AOP 反射获取 Service Bean
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    @Value("${spring.application.name}")
    private String applicationName;

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public String getApplicationName() {
        return applicationName;
    }

}
