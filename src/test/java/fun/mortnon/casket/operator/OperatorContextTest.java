package fun.mortnon.casket.operator;

import fun.mortnon.casket.TestDataSource;
import fun.mortnon.casket.User;
import fun.mortnon.casket.UserType;
import fun.mortnon.casket.exception.DbException;
import fun.mortnon.casket.orm.SubOperator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */

class OperatorContextTest {

    private static DataSource dataSource;
    private static OperatorContext operatorContext;
    private static UserDAO userDAO;

    @BeforeAll
    public static void init() {
        dataSource = new TestDataSource();
        operatorContext = new OperatorContext(dataSource);
        userDAO = operatorContext.create(UserDAO.class);
    }

    @Test
    public void testAnno() {
        List<User> haha = userDAO.select("haha");
        assertEquals(0, haha.size());

        List<User> awu = userDAO.select("1");
        assertEquals(1, awu.size());
        assertEquals("awu", awu.get(0).getName());
    }

    @Test
    public void testSelectAll() {
        List<User> strings = userDAO.select();
        assertEquals(2, strings.size());
    }

    @Test
    public void testSelect() {
        List<String> strings = userDAO.selectNameById(1);
        assertEquals(1, strings.size());
    }

    @Test
    public void testSelect2() {
        List<String> strings = userDAO.selectNameById2(1);
        assertEquals(1, strings.size());
    }

    @Test
    public void testSelect3() {
        List<Integer> strings = userDAO.selectIdByName("awu");
        assertEquals(1, strings.size());
    }

    @Test
    public void testSelect4() {
        User awu = userDAO.selectOne("awu");
        assertNotNull(awu);
        assertEquals(1, awu.getId());
        assertEquals("awu", awu.getName());
        assertEquals(UserType.Admin, awu.getType());
    }

    @Test
    public void testSubSelect() {
        SubOperator subOperator = operatorContext.create(SubOperator.class);
        List<User> users = subOperator.selectAll();
    }

    @Test
    public void testWrongCode() {
        assertThrows(DbException.class, () -> userDAO.selectWrong("awu"));
    }


}