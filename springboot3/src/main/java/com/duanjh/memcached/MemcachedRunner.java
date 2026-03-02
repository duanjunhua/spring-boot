package com.duanjh.memcached;

import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-02 周一 16:12
 * @Version: v1.0
 * @Description: Memcached初始化
 */
@Slf4j
@Getter
@Component
public class MemcachedRunner implements CommandLineRunner {

    @Resource
    MemcacheSource source;

    MemcachedClient client = null;

    @Override
    public void run(String... args) throws Exception {

        try {
            log.info("初始化Memcached客户端");
            client = new MemcachedClient(new InetSocketAddress(source.getHost(), source.getPort()));
        }catch (Exception e){
            log.error("初始化MemcachedClient失败，失败信息{}", e.getLocalizedMessage());
        }
    }
}
