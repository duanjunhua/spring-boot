package com.duanjh.memcached;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-02 周一 16:11
 * @Version: v1.0
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "memcached")
public class MemcacheSource {

    private String host;

    private int port;

}
