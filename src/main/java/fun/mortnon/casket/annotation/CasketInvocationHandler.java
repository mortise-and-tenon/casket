package fun.mortnon.casket.annotation;

import fun.mortnon.casket.common.Operator;
import fun.mortnon.casket.operator.SelectOperator;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public class CasketInvocationHandler extends AbstractInvocationHandler {

    private Class<?> daoClass;
    private DataSource dataSource;

    public CasketInvocationHandler(Class<?> daoClass, DataSource dataSource) {
        this.daoClass = daoClass;
        this.dataSource = dataSource;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] annotations = method.getAnnotations();
        if (method.isAnnotationPresent(Select.class)) {
            Select selectAnno = method.getAnnotation(Select.class);
            String column = selectAnno.column();
            Operator operator = selectAnno.operator();
            Class<?> entityClz = selectAnno.entity();
            SelectOperator selectOperator = new SelectOperator(dataSource, operator);
            if (args.length == 1) {
                return selectOperator.select(column, args[0], entityClz);
            }
        }
        return null;
    }
}
