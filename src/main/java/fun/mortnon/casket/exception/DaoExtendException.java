package fun.mortnon.casket.exception;

import fun.mortnon.casket.orm.BaseEntityOperator;

/**
 * @author Moon Wu
 * @date 2022/8/4
 */
public class DaoExtendException extends RuntimeException {

    public DaoExtendException(String className) {
        this(className, null);
    }

    public DaoExtendException(String className, Throwable cause) {
        super("Class [" + className + "] is not sub class of " + BaseEntityOperator.class.getName(), cause);
    }
}
