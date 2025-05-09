package com.spring.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;

/**
 * jdbc模板
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public abstract class JdbcTemplate {
    private DataSource dataSource;

    public JdbcTemplate() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Object query(StatementCallBack statementCallBack) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();

            return statementCallBack.doInStatement(stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Object query(String sql, Object[] args, PreparedStatementCallBack preparedStatementCallback) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof String) {
                    stmt.setString(i + 1, (String) arg);
                } else if (arg instanceof Long) {
                    stmt.setLong(i + 1, (Long) arg);
                }
            }

            return preparedStatementCallback.doInPreparedStatement(stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected abstract Object doInStatement(ResultSet res);
}
