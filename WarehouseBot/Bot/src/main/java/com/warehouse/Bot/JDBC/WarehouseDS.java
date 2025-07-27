package com.warehouse.Bot.JDBC;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
@Configuration
@ComponentScan
public class WarehouseDS {
    @Bean
    public static DataSource PostGreSQLDataSource(){
        DataSourceBuilder<?> dsb = DataSourceBuilder.create();
        dsb.driverClassName("org.postgresql.Driver");
        dsb.url("jdbc:postgresql://localhost:5432/postgres?currentSchema=\"Warehouse\"");
        dsb.username("postgres");
        dsb.password("Tangra");

        return dsb.build();
    }
}
