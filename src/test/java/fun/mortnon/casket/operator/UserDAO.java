package fun.mortnon.casket.operator;

import fun.mortnon.casket.User;
import fun.mortnon.casket.annotation.Casket;
import fun.mortnon.casket.annotation.Select;

import java.util.List;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
@Casket(table = "user")
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
}
