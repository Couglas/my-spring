package com.spring.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * statement回调
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public interface StatementCallBack {
    Object doInStatement(Statement stmt) throws SQLException;
}
