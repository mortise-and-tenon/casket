package fun.mortnon.casket.annotation;

import fun.mortnon.casket.exception.DaoExtendException;
import fun.mortnon.casket.operator.SelectOperator;
import fun.mortnon.casket.orm.BaseEntityOperator;
import org.apache.commons.lang3.StringUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import javax.persistence.Table;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Operator operatorAnno = daoClass.getAnnotation(Operator.class);
        String tableName = operatorAnno.table();

        if (method.isAnnotationPresent(Select.class)) {
            Select selectAnno = method.getAnnotation(Select.class);
            String conditionColumn = selectAnno.conditionColumn();
            String[] selectColumns = selectAnno.selectColumns();

            String selectTableName = selectAnno.table();
            if (StringUtils.isNotEmpty(selectTableName)) {
                tableName = selectTableName;
            }

            fun.mortnon.casket.common.Operator operator = selectAnno.operator();
            Class<?> returnClazz = method.getReturnType();
            boolean isList = false;
            if (returnClazz == List.class) {
                Type type = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
                if (type instanceof TypeVariableImpl) {
                    Type[] interfaces = daoClass.getGenericInterfaces();
                    Type[] operatorType = Arrays.stream(interfaces).filter(k -> ((ParameterizedType) k).getRawType() == BaseEntityOperator.class)
                            .map(m -> ((ParameterizedTypeImpl) m).getActualTypeArguments())
                            .findFirst().orElse(null);
                    if (null == operatorType) {
                        throw new DaoExtendException(daoClass.getName());
                    }

                    returnClazz = (Class) operatorType[0];
                } else {
                    returnClazz = (Class) type;
                }
                isList = true;
            }

            Table tableAnno = returnClazz.getAnnotation(Table.class);

            tableName = Optional.ofNullable(tableAnno).map(Table::name).orElse(tableName);

            SelectOperator selectOperator = new SelectOperator(dataSource, method, operator);
            if (isList) {
                return selectOperator.select(conditionColumn, Arrays.asList(args), Arrays.asList(selectColumns), returnClazz, tableName);
            }

            return selectOperator.selectOne(conditionColumn, Arrays.asList(args), Arrays.asList(selectColumns), returnClazz, tableName);
        }
        return null;
    }
}
