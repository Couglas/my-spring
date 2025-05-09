package com.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 结果集映射器
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public interface ResultSetExtractor<T> {
    List<T> extractData(ResultSet rs) throws SQLException;
}
