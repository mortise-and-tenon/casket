package fun.mortnon.casket.orm;

import fun.mortnon.casket.TestDataSource;
import fun.mortnon.casket.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Moon Wu
 * @date 2022/8/5
 */
class OperatorManagerTest {
    private static OperatorManager operatorManager;

    @BeforeAll
    public static void init() {
        operatorManager = new OperatorManager(new TestDataSource());
    }

    @Test
    void instance() {
        SubOperator instance = operatorManager.instance(SubOperator.class);
        List<User> users = instance.selectAll();
        assertEquals(2, users.size());
    }
}