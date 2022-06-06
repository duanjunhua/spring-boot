package com.boot.springboot.config.jackson;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description: json格式化配置
 */
@Configuration
public class JacksonConfig extends WebMvcConfigurationSupport {

    /**
     * 格式化输出配置
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for(HttpMessageConverter converter : converters){
            if(converter instanceof MappingJackson2HttpMessageConverter){
                MappingJackson2HttpMessageConverter jacksonConvert = (MappingJackson2HttpMessageConverter) converter;
                jacksonConvert.setPrettyPrint(true);
            }
        }
        super.extendMessageConverters(converters);
    }
}
