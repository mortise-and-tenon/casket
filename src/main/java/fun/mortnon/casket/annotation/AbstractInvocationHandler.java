package fun.mortnon.casket.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public abstract class AbstractInvocationHandler implements InvocationHandler {
    private static final Object[] NO_ARGS = {};

    private static final String METHOD_HASH_CODE = "hashCode";
    private static final String METHOD_EQUALS = "equals";
    private static final String METHOD_TO_STRING = "toString";

    @Override
    public final Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (args == null) {
            args = NO_ARGS;
        }

        if (args.length == 0 && METHOD_EQUALS.equals(method.getName())) {
            return hashCode();
        }

        if (args.length == 1
                && METHOD_EQUALS.equals(method.getName())
                && method.getParameterTypes()[0] == Object.class) {
            Object arg = args[0];
            return proxy.getClass().isInstance(arg) && equals(Proxy.getInvocationHandler(arg));
        }

        if (args.length == 0 && METHOD_TO_STRING.equals(method.getName())) {
            return toString();
        }

        return handleInvocation(proxy, method, args);
    }

    /**
     * 标准方法反射
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    protected abstract Object handleInvocation(Object proxy, Method method, Object[] args)
            throws Throwable;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
