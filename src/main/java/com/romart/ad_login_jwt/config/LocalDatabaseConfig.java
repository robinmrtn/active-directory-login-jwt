package com.romart.ad_login_jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(basePackages = "com.romart.ldapdemo.Repository.local",
        entityManagerFactoryRef = "localEntityManager", transactionManagerRef = "localTransactionManager")
public class LocalDatabaseConfig {

    @Autowired
    private Environment env;

    public LocalDatabaseConfig() {
        super();
    }

    @Bean
    @Primary
    public DataSource localDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.local-datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.local-datasource.url"));
        dataSource.setUsername(env.getProperty("spring.local-datasource.username"));
        dataSource.setPassword(env.getProperty("spring.local-datasource.password"));

        return dataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean localEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(localDataSource());
        em.setPackagesToScan("com.romart.ldapdemo.Domain.local");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager localTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localEntityManager().getObject());
        return transactionManager;
    }
}
