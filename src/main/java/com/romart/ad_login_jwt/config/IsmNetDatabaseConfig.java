package com.romart.ad_login_jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "com.romart.ldapdemo.repository.ismnet",
        entityManagerFactoryRef = "ismnetEntityManager", transactionManagerRef = "ismnetTransactionManager")
public class IsmNetDatabaseConfig {

    @Autowired
    private Environment env;

    public IsmNetDatabaseConfig() {
        super();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.ismnet-datasource")
    public DataSource ismnetDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.ismnet-datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.ismnet-datasource.url"));
        dataSource.setUsername(env.getProperty("spring.ismnet-datasource.username"));
        dataSource.setPassword(env.getProperty("spring.ismnet-datasource.password"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ismnetEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ismnetDataSource());
        em.setPackagesToScan("com.romart.ldapdemo.domain.ismnet");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager ismnetTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ismnetEntityManager().getObject());
        return transactionManager;
    }
}
