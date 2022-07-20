package com.boot.config;

import com.boot.util.MinioUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    public String endpoint;

    public String bucketName;

    public String accessKey;

    public String secretKey;

    @Bean
    MinioUtil minioUtil(){
        MinioUtil client =  new MinioUtil(this.endpoint, this.bucketName, this.accessKey, this.secretKey, null, null);
        client.createMinioClient();
        return client;
    }
}
