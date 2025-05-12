package com.test.service;

import com.spring.batis.SqlSession;
import com.spring.batis.SqlSessionFactory;
import com.spring.beans.factory.annotation.Autowired;
import com.spring.jdbc.core.JdbcTemplate;
import com.test.entity.User;

import java.sql.ResultSet;
import java.util.List;

/**
 * 用户服务
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public User getUser(Long userId) {
        String sql = "select * from user where id = " + userId;
        return (User) jdbcTemplate.query(stmt -> {
            ResultSet res = stmt.executeQuery(sql);
            if (res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                return user;
            }
            return null;
        });
    }

    public User getUserInfo(Long userId) {
        String sqlId = "com.test.entity.User.getUserInfo";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return (User) sqlSession.selectOne(sqlId, new Object[]{userId}, stmt -> {
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                return user;
            }

            return null;
        });
    }

    public List<User> getUserList(Long userId) {
        String sql = "select * from user where id > ? ";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            System.out.println(rowNum);
            return user;
        });
    }
}
