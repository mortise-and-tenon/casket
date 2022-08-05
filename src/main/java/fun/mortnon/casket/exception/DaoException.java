package fun.mortnon.casket.exception;

public class DaoException extends RuntimeException {
    public DaoException(String methodName) {
        this(methodName, null);
    }

    public DaoException(String methodName, Throwable cause) {
        super("Could not get suitable return column,check @Select or function return type in method [" + methodName + "] ", cause);
    }
}
