package com.test.service;

import com.spring.jdbc.core.JdbcTemplate;
import com.test.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户jdbc实现类
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public class UserJdbcImpl extends JdbcTemplate {

    @Override
    protected Object doInStatement(ResultSet res) {
        User user = null;

        try {
            if (res.next()) {
                user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
