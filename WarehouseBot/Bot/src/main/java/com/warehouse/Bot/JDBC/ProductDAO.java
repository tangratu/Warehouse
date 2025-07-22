package com.warehouse.Bot.JDBC;

import objects.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDAO {
    private final DataSource ds;
    private final JdbcClient jc;

    @Autowired
    public ProductDAO(DataSource d){
        ds=d;
        jc = JdbcClient.create(ds);
    }

    public List<Product> getAll(){
        String
    }

}
