package com.mtucholski.reservation.app.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryRead",
        transactionManagerRef = "transactionManagerRead",
        basePackages = "com.mtucholski.reservation.app.repository.read"
)
public class DataSourceConfigRead extends HikariConfig {

    @Bean
    @ConfigurationProperties("spring.datasource-read")
    public DataSourceProperties readOnlyProperties(){

        return new DataSourceProperties();
    }

    @Bean(name = "readDataSource")
    @ConfigurationProperties("spring.datasource-read.hikari")
    public DataSource readOnlyDataSource(){

        return readOnlyProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "entityManagerFactoryRead")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder){

        return builder
                .dataSource(readOnlyDataSource())
                .packages("com.mtucholski.reservation.app.model")
                .build();
    }

    @Bean(name="transactionManagerRead")
    public PlatformTransactionManager transactionManagerRead(
            final @Qualifier("entityManagerFactoryRead") EntityManagerFactory entityManagerFactoryRead) {
        return new JpaTransactionManager(entityManagerFactoryRead);
    }
}
