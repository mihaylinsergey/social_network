package ru.otus.social_network.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Profile("haproxy")
public class HaproxyDataSourceConfig {
    @Bean("masterDataSourceProperties")
    @ConfigurationProperties("spring.datasource.master")
    public DataSourceProperties masterDataSourceProperties()  {
        return new DataSourceProperties();
    }

    @Bean("masterDataSource")
    public DataSource masterDataSource(@Qualifier("masterDataSourceProperties") DataSourceProperties properties) {
        return properties
                .initializeDataSourceBuilder()
                .build();
    }

    @Primary
    @Bean("masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean("slaveOneDataSourceProperties")
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSourceProperties slaveOneDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("slaveOneDataSource")
    public DataSource slaveOneDataSource(@Qualifier("slaveOneDataSourceProperties") DataSourceProperties properties) {
        return properties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean("slaveOneJdbcTemplate")
    public JdbcTemplate slaveOneJdbcTemplate(@Qualifier("slaveOneDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean("slaveTwoDataSourceProperties")
    @ConfigurationProperties("spring.datasource.slave2")
    public DataSourceProperties slaveTwoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("slaveTwoDataSource")
    public DataSource slaveOTwDataSource(@Qualifier("slaveTwoDataSourceProperties") DataSourceProperties properties) {
        return properties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean("slaveTwoJdbcTemplate")
    public JdbcTemplate slaveTwoJdbcTemplate(@Qualifier("slaveTwoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
