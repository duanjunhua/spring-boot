package com.duanjh.springdoc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-16 周一 09:45
 * @Version: v1.0
 * @Description: SpringDoc自定义配置（可不配使用默认的）
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI(){

        return new OpenAPI()
                // 自定义接口文档信息
                .info(new Info().title("Personal Spring Doc")
                        .description("自定义SpringDoc配置")
                        .version("v1.0.0")

                        // 自定义地址信息，如单位链接等
                        .license(new License()
                                .name("duanjunhua projects")
                                .url("https://github.com/duanjunhua")
                        )
                )
                // 其他外部文档
                .externalDocs(new ExternalDocumentation().description("Project Location").url("https://github.com/duanjunhua"));

    }

}
