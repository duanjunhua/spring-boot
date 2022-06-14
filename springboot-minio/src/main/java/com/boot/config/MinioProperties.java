package com.boot.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-09
 * @Version: V1.0
 * @Description: MINIO配置
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String endpoint;

    private String bucketName;

    private String accessKey;

    private String secretKey;

}
