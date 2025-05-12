package com.spring.batis;

import com.spring.jdbc.core.JdbcTemplate;
import com.spring.jdbc.core.PreparedStatementCallBack;

/**
 * 默认sqlSession
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public class DefaultSqlSession implements SqlSession {
    private JdbcTemplate jdbcTemplate;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlId, Object[] args, PreparedStatementCallBack preparedStatementCallBack) {
        String sql = this.sqlSessionFactory.getMapperNode(sqlId).getSql();
        return jdbcTemplate.query(sql, args, preparedStatementCallBack);
    }
}
