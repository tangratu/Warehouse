package com.warehouse.Bot.RowMappers;

import com.warehouse.Bot.objects.SessionUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionUserMapper implements RowMapper<SessionUser> {
    public SessionUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        SessionUser res = new SessionUser();
        res.setName(rs.getString("name"));
        res.setAddress(rs.getString("address"));
        res.setEmail(rs.getString("email"));
        return res;
    }
}
