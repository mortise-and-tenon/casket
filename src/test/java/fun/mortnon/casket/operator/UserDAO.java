package fun.mortnon.casket.operator;

import fun.mortnon.casket.User;
import fun.mortnon.casket.annotation.Operator;
import fun.mortnon.casket.annotation.Select;
import fun.mortnon.casket.annotation.Update;

import java.util.List;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
@Operator(table = "user")
public interface UserDAO {
    @Select
    List<User> select();

    @Select(conditionColumn = "id")
    List<User> select(String name);

    @Select(conditionColumn = "id", selectColumns = "name", table = "user")
    List<String> selectNameById(int id);

    @Select(conditionColumn = "id", selectColumns = "name")
    List<String> selectNameById2(int id);

    @Select(conditionColumn = "name", selectColumns = "id")
    List<Integer> selectIdByName(String name);

    @Select(conditionColumn = "name")
    User selectOne(String name);

    @Select
    User selectWrong(String name);

    @Update
    boolean updateUser(User user);

}
