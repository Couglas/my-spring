package com.spring.jdbc.core;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 参数包装器
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public class ArgumentPreparedStatementSetter {
    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }


    public void setValues(PreparedStatement stmt) throws SQLException {
        if (this.args != null) {
            for (int i = 0; i < this.args.length; i++) {
                Object arg = this.args[i];
                doSetValue(stmt, i + 1, arg);
            }
        }
    }

    private void doSetValue(PreparedStatement stmt, int parameterPosition, Object arg) throws SQLException {
        if (arg instanceof String) {
            stmt.setString(parameterPosition, (String) arg);
        }
        if (arg instanceof Long) {
            stmt.setLong(parameterPosition, (Long) arg);
        }
        if (arg instanceof Date) {
            stmt.setDate(parameterPosition, new java.sql.Date(((Date) arg).getTime()));
        }
    }
}
