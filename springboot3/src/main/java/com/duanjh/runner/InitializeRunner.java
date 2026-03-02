package com.duanjh.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-02 周一 15:56
 * @Version: v1.0
 * @Description: 用于项目启动初始化的操作，如线程池初始化、加载加密证书等。可以有多个CommandLineRunner实现类，通过@Order(num)来指定加载顺序，num值越小启动顺序越靠前
 */
@Slf4j
@Component
public class InitializeRunner implements CommandLineRunner {

    /**
     * 会在容器加载之后执行，执行完成后项目启动完成
     */
    @Override
    public void run(String... args) throws Exception {
        //TODO:

        log.info("初始化各类资源");

    }
}
