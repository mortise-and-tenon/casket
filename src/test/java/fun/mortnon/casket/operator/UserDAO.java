package fun.mortnon.casket.operator;

import fun.mortnon.casket.User;
import fun.mortnon.casket.annotation.Casket;
import fun.mortnon.casket.annotation.Select;

import java.util.List;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
@Casket
public interface UserDAO {
    @Select(column = "id", entity = User.class)
    List<User> select(String name);
}
