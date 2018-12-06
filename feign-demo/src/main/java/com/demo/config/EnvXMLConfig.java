package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Michael
 * @Date: 2018年12月6日_下午4:55:40
 * @Version: v0.1
 */
@Configuration
@ImportResource(locations= {"classpath:bean/application-bean.xml"})
public class EnvXMLConfig {

}
