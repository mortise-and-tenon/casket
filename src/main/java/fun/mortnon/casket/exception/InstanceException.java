package fun.mortnon.casket.exception;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public class InstanceException extends RuntimeException {
    private Class beanClass;

    public InstanceException(Class beanClass, String msg) {
        this(beanClass, msg, null);
    }

    public InstanceException(Class beanClass, String msg, Throwable cause) {
        super("Could not instantiate bean class [" + beanClass.getName() + "]: " + msg, cause);
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }
}
