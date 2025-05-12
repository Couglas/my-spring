package com.spring.batis;

import com.spring.jdbc.core.JdbcTemplate;
import com.spring.jdbc.core.PreparedStatementCallBack;

/**
 * sqlSession
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public interface SqlSession {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    Object selectOne(String sqlId, Object[] args, PreparedStatementCallBack preparedStatementCallBack);
}
