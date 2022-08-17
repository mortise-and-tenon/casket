package fun.mortnon.casket.operator;

import com.mysql.cj.jdbc.ClientPreparedStatement;
import fun.mortnon.casket.exception.DbException;
import fun.mortnon.casket.extractor.sql.BoundSql;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
@Slf4j
public class DatabaseExecutor {
    protected DataSource dataSource;
    protected Connection connection;

    public DatabaseExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
        getConnection();
    }

    private void getConnection() {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            log.error("get connection fail for ", e);
        }
    }


    public ResultSet select(BoundSql sql) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.getSql());
            List<Object> parameters = sql.getParameters();
            int index = 1;
            for (Object parameter : parameters) {
                bindParameter(preparedStatement, index, parameter);
                index++;
            }
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            DbException.sqlError(e);
        }
        return null;
    }

    private void bindParameter(PreparedStatement ps, int index, Object value) throws SQLException {
        if (value instanceof String) {
            ps.setString(index, (String) value);
        } else if (value instanceof Integer) {
            ps.setInt(index, (Integer) value);
        }
    }
}
