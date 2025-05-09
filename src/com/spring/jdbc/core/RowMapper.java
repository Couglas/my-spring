package com.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果行映射器
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
