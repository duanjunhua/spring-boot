package com.duanjh.tess4j;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-01 周三 09:57
 * @Version: v1.0
 * @Description: 图像文字识别配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tess4j")
public class Tess4JConfig {

    private String dataPath;

    private String chineseTrainData;

    private String englishTrainData;

}