package ru.otus.social_network.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import ru.otus.social_network.routingdatasource.ReplicationRoutingDataSource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Replication DataSources Configuration
 *
 * <code>@Primary</code> and <code>@DependsOn</code> are the key requirements for Spring Boot.
 */
@Profile("replication")
@Configuration
public class ReplicationDataSourceConfig {
    /**
     * Main DataSource
     * <p>
     * Application must use this dataSource.
     */
    @Primary
    @Bean
    // @DependsOn required!! thanks to Michel Decima
    @DependsOn({"writeDataSource", "readDataSource", "routingDataSource"})
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    /**
     * AbstractRoutingDataSource and it's sub classes must be initialized as Spring Bean for calling
     * {@link AbstractRoutingDataSource#afterPropertiesSet()}.
     */
    @Bean
    public DataSource routingDataSource() {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", writeDataSource());
        dataSourceMap.put("read", readDataSource());
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource());

        return routingDataSource;
    }

    @Bean(destroyMethod = "")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:15431/social_network")
                .username("postgres")
                .password("password")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean(destroyMethod = "")
    public DataSource readDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:15432/social_network")
                .username("postgres")
                .password("password")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
