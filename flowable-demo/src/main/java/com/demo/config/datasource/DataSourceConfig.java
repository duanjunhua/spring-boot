package com.demo.config.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author Michael
 * @Date: 2018年12月7日_下午2:54:13
 * @Version: v0.1
 */
@Configuration
public class DataSourceConfig {

	@Value("${datasource.dev.driveClassName}")
	private String driveClassName;
	
	@Value("${datasource.dev.url}")
	private String url;
	
	@Value("${datasource.dev.username}")
	private String userName;
	
	@Value("${datasource.dev.password}")
	private String password;
	
	@Bean
	public DataSource createDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driveClassName);
		return dataSource;
	}
}
