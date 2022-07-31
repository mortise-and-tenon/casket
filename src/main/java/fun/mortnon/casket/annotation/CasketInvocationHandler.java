package fun.mortnon.casket.annotation;

import fun.mortnon.casket.common.Operator;
import fun.mortnon.casket.operator.SelectOperator;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Casket casketAnno = daoClass.getAnnotation(Casket.class);
        String tableName = casketAnno.table();

        if (method.isAnnotationPresent(Select.class)) {
            Select selectAnno = method.getAnnotation(Select.class);
            String conditionColumn = selectAnno.conditionColumn();
            String[] selectColumns = selectAnno.selectColumns();

            String selectTableName = selectAnno.table();
            if (StringUtils.isNotEmpty(selectTableName)) {
                tableName = selectTableName;
            }

            Operator operator = selectAnno.operator();
            Class<?> returnClazz = method.getReturnType();
            if (returnClazz == List.class) {
                Type type = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
                returnClazz = (Class) type;
            }

            SelectOperator selectOperator = new SelectOperator(dataSource, method, operator);
            if (args.length == 1) {
                return selectOperator.select(conditionColumn, args[0], Arrays.asList(selectColumns), returnClazz, tableName);
            } else {
                return selectOperator.select(conditionColumn, Arrays.asList(args), Arrays.asList(selectColumns), returnClazz, tableName);
            }
        }
        return null;
    }
}
