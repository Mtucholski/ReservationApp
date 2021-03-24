package com.mtucholski.reservation.app.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryWrite",
        transactionManagerRef = "transactionManagerWrite",
        basePackages = "com.mtucholski.reservation.app.repository.write")
public class DataSourceConfigWrite extends HikariConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource-write")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name="writeDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource-write.hikari")
    public DataSource writeDataSource() {
        return writeDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "entityManagerFactoryWrite")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryWrite(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(writeDataSource())
                .packages("com.mtucholski.reservation.app.model")
                .build();
    }
    @Bean(name="transactionManagerWrite")
    public PlatformTransactionManager transactionManagerWrite(
            final @Qualifier("entityManagerFactoryWrite") EntityManagerFactory entityManagerFactoryWrite) {
        return new JpaTransactionManager(entityManagerFactoryWrite);
    }
}
