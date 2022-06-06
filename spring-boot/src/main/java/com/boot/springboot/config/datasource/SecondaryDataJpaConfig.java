package com.boot.springboot.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-30
 * @Version: V1.0
 * @Description: Spring Data Jpa 多数据源配置
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySecondary",
        transactionManagerRef = "transactionManagerSecondary",
        basePackages = {"com.boot.springboot.dao.secondary"}   //设置Repository所在位置
)
public class SecondaryDataJpaConfig {

    @Autowired
    @Qualifier("secondaryDataSource")
    DataSource dataSource;

    @Bean(name = "entityManagerFactorySecondary")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManager()  {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        // 更改为对应包名，包括 repository 及 pojo ，建议放到同级不同子目录
        factory.setPackagesToScan("com.boot.springboot.domain.secondary");
        factory.setDataSource(dataSource);
        factory.setJpaProperties(jpaProperties());
        return factory;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        return properties;
    }

    @Bean(name = "transactionManagerSecondary")
    public PlatformTransactionManager secondaryTransactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(secondaryEntityManager().getObject());
        return txManager;
    }
}
