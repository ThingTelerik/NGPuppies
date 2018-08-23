package com.example.demo.config;

import java.util.Properties;
import java.util.TimeZone;

import javax.sql.DataSource;

import com.example.demo.data.HibernateUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 ** config data source , entity manager here
 */

@Configuration
@EnableJpaRepositories( basePackages = {"com.example.demo.data" })
@EnableTransactionManagement
@EntityScan("com.example.demo.entities")
public class DataConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/usersdemo");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.example.demo.entities");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(jpaProperties());

        return factory;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLMyISAMDialect");
        properties.put(AvailableSettings.JDBC_TIME_ZONE,
                TimeZone.getTimeZone( "UTC" ));// MySQL5InnoDBDialect   MySQLMyISAMDialect
        properties.put("hibernate.show_sql", "true");
        return properties;
    }
    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }


}
