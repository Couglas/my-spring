package com.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 结果映射实现类
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<T> {
    private final RowMapper<T> rowMapper;

    public RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> results = new ArrayList<>();
        int rowNum = 0;
        while (rs.next()) {
            results.add(this.rowMapper.mapRow(rs, rowNum++));
        }
        return results;
    }
}
