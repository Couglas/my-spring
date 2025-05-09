package com.spring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * prepared statement 回调
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public interface PreparedStatementCallBack {
    Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}
