package com.warehouse.Bot.RowMappers;

import com.warehouse.Bot.objects.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order res = new Order();
        res.setId(rs.getInt("id"));
        res.setStatus(rs.getString("status"));
        res.setMessage(rs.getString("message"));
        res.setContents(rs.getString("contents"));
        return res;
    }
}
