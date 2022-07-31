package fun.mortnon.casket.operator;

import fun.mortnon.casket.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */

class OperatorContextTest {

    private static DataSource dataSource;

    @BeforeAll
    public static void init() {
        dataSource = new TestDataSource();
    }

    @Test
    public void testAnno() {
        OperatorContext operatorContext = new OperatorContext(new TestDataSource());
        UserDAO userDAO = operatorContext.create(UserDAO.class);
        List<User> haha = userDAO.select("haha");
        assertEquals(0, haha.size());

        List<User> awu = userDAO.select("1");
        assertEquals(1, awu.size());
        assertEquals("awu", awu.get(0).getName());
    }

    @Test
    public void testSelectAll() {
        OperatorContext operatorContext = new OperatorContext(new TestDataSource());
        UserDAO userDAO = operatorContext.create(UserDAO.class);
        List<User> strings = userDAO.select();
        assertEquals(2, strings.size());
    }

    @Test
    public void testSelect() {
        OperatorContext operatorContext = new OperatorContext(new TestDataSource());
        UserDAO userDAO = operatorContext.create(UserDAO.class);
        List<String> strings = userDAO.selectNameById(1);
        assertEquals(1, strings.size());
    }

    @Test
    public void testSelect2() {
        OperatorContext operatorContext = new OperatorContext(new TestDataSource());
        UserDAO userDAO = operatorContext.create(UserDAO.class);
        List<String> strings = userDAO.selectNameById2(1);
        assertEquals(1, strings.size());
    }

    @Test
    public void testSelect3() {
        OperatorContext operatorContext = new OperatorContext(new TestDataSource());
        UserDAO userDAO = operatorContext.create(UserDAO.class);
        List<Integer> strings = userDAO.selectIdByName("awu");
        assertEquals(1, strings.size());
    }

    static class TestDataSource implements DataSource {

        @Override
        public Connection getConnection() throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mortnon", "root", "123456");
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            return null;
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {

        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {

        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }
    }
}