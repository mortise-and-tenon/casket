package fun.mortnon.casket;


import java.util.List;

/**
 * @author Moon Wu
 * @date 2022/7/26
 */
public class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    enum UserType {
        User,
        Admin
    }
}
