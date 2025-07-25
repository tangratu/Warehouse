package com.warehouse.Bot.RowMappers;

import com.warehouse.Bot.objects.Cell;
import com.warehouse.Bot.objects.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs,int rowNumber) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));
        p.setQuantity(rs.getInt("quantity"));
        p.setLocation(new Cell(rs.getInt("X"),rs.getInt("Y")));
        return p;
    }
}
