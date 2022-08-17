package fun.mortnon.casket.exception;

/**
 * @author Moon Wu
 * @date 2022/8/5
 */
public class DbException extends RuntimeException {
    public DbException(String message) {
        this(message, null);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void sqlError(Throwable cause) {
        throw new DbException("check the method parameters are not match the @Select conditionColumn count or the cause exception.", cause);
    }
}
