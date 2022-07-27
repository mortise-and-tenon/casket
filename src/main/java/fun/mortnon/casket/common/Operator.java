package fun.mortnon.casket.common;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public enum Operator {
    Equal("="),
    NotEqual("<>"),
    In("IN"),
    NotIn("NOT IN");

    private String descriptor;

    Operator(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return this.descriptor;
    }
}
