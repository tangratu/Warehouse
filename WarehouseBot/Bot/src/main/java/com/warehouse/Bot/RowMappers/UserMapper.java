package com.warehouse.Bot.RowMappers;

import com.warehouse.Bot.objects.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User res = new User();
        res.setId(rs.getInt("id"));
        res.setName(rs.getString("name"));
        res.setAddress(rs.getString("address"));
        res.setPassword(rs.getString("password"));
        res.setEmail(rs.getString("email"));
        return res;
    }
}
