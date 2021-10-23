package com.wootech.dropthecode.config;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.wootech.dropthecode.repository.replication.CustomDataSourceProperties;
import com.wootech.dropthecode.repository.replication.ReplicationRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("except-prod")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(CustomDataSourceProperties.class)
public class CustomDataSourceConfig {

    private final CustomDataSourceProperties databaseProperty;
    private final JpaProperties jpaProperties;

    public CustomDataSourceConfig(CustomDataSourceProperties databaseProperty, JpaProperties jpaProperties) {
        this.databaseProperty = databaseProperty;
        this.jpaProperties = jpaProperties;
    }

    public DataSource createDataSource(String url) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(databaseProperty.getUsername());
        hikariDataSource.setPassword(databaseProperty.getPassword());
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setMaximumPoolSize(databaseProperty.getMaximumPoolSize());

        return hikariDataSource;
    }

    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    @Bean
    public DataSource routingDataSource() {
        DataSource master = createDataSource(databaseProperty.getUrl());

        Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
        dataSourceMap.put("master", master);
        databaseProperty.getSlave()
                        .forEach((key, value) -> dataSourceMap.put(value.getName(), createDataSource(value.getUrl())));

        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
        replicationRoutingDataSource.setDefaultTargetDataSource(master);
        replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
        return replicationRoutingDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        EntityManagerFactoryBuilder entityManagerFactoryBuilder = createEntityManagerFactoryBuilder(jpaProperties);
        return entityManagerFactoryBuilder.dataSource(dataSource()).packages("com.wootech.dropthecode").build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
        AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        return tm;
    }
}
