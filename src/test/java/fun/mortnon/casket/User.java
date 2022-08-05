package fun.mortnon.casket;


import javax.persistence.Table;

/**
 * @author Moon Wu
 * @date 2022/7/26
 */
@Table(name = "user")
public class User {
    private int id;
    private String name;
    private UserType type;

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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
