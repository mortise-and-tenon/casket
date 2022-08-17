package fun.mortnon.casket.operator;

import fun.mortnon.casket.User;
import fun.mortnon.casket.annotation.Operator;
import fun.mortnon.casket.annotation.Select;
import fun.mortnon.casket.annotation.Update;
import fun.mortnon.casket.annotation.Where;
import fun.mortnon.casket.common.Condition;

import java.util.List;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
@Operator(table = "user")
public interface UserDAO {
    @Select
    List<User> select();

    @Select
    List<User> select(@Where(column = "name") String name);

    @Select
    List<User> selectByName(@Where(column = "name", operator = Condition.In) List<String> names);

    @Select
    List<User> selectMultiCondition(@Where(column = "name") String name, @Where(column = "id") int id);

    @Select(columns = "name", from = "user")
    List<String> selectNameById(@Where(column = "id") int id);

    @Select(columns = "name")
    List<String> selectNameById2(@Where(column = "id") int id);

    @Select(columns = "id")
    List<Integer> selectIdByName(@Where(column = "name") String name);

    @Select
    User selectOne(@Where(column = "name") String name);

    @Select
    User selectWrong(String name);

    @Update
    boolean updateUser(User user);

}
