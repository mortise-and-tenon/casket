package fun.mortnon.casket.operator;

import fun.mortnon.casket.common.Condition;
import fun.mortnon.casket.exception.DaoException;
import fun.mortnon.casket.extractor.BaseResultSetExtractor;
import fun.mortnon.casket.extractor.ResultSetExtractor;
import fun.mortnon.casket.extractor.sql.BoundSql;
import fun.mortnon.casket.extractor.sql.ConditionWrapper;
import fun.mortnon.casket.reflect.Reflection;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static fun.mortnon.casket.common.Constants.COMMA;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public class SelectOperator<T> {
    private final String SELECT_SQL = "SELECT %s FROM %s ";
    private final String WHERE_SQL = " WHERE %s ";
    private final String CONDITION_SQL = " %s %s %s %s";
    private DataSource dataSource;
    private Method method;

    private List<ConditionWrapper> conditionWrappers;

    public SelectOperator(DataSource dataSource, Method method) {
        this(dataSource, method, null);
    }

    public SelectOperator(DataSource dataSource, Method method, List<ConditionWrapper> conditionWrappers) {
        this.dataSource = dataSource;
        this.method = method;
        this.conditionWrappers = conditionWrappers;
    }

    public <T> List<T> select(Class<T> returnClazz, String tableName) throws SQLException {
        return select(null, returnClazz, tableName);
    }

    public <T> List<T> select(List<String> selectColumns, Class<T> returnClazz, String tableName) throws SQLException {
        BoundSql boundSql = analyze(selectColumns, returnClazz, tableName);
        ResultSet resultSet = new DatabaseExecutor(dataSource).select(boundSql);
        ResultSetExtractor extractor = new BaseResultSetExtractor();
        return extractor.extract(resultSet, returnClazz);
    }

    public <T> T selectOne(List<String> selectColumns, Class<T> returnClazz,
                           String tableName) throws SQLException {
        BoundSql boundSql = analyze(selectColumns, returnClazz, tableName);
        ResultSet resultSet = new DatabaseExecutor(dataSource).select(boundSql);

        if (null == resultSet) {
            return null;
        }

        ResultSetExtractor extractor = new BaseResultSetExtractor();
        return extractor.extractOne(resultSet, returnClazz);
    }

    private <T> BoundSql analyze(List<String> selectColumns, Class<T> returnClazz, String tableName) {
        String fieldsSql = "";
        if (ObjectUtils.isNotEmpty(selectColumns)) {
            fieldsSql = StringUtils.join(selectColumns, COMMA);
        }
        if (StringUtils.isEmpty(fieldsSql)) {
            Map<String, Type> fields = Reflection.getColumns(returnClazz);
            fieldsSql = StringUtils.join(fields.keySet(), COMMA);
        }

        if (StringUtils.isEmpty(fieldsSql)) {
            throw new DaoException(this.method.toString());
        }

        if (StringUtils.isEmpty(tableName)) {
            tableName = Reflection.getTableName(returnClazz);
        }

        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(String.format(SELECT_SQL, fieldsSql, tableName));

        StringBuilder conditionSb = new StringBuilder();

        List<Object> parameters = new ArrayList<>();
        boolean firstParam = true;
        for (ConditionWrapper wrapper : conditionWrappers) {
            StringBuilder condition = new StringBuilder();
            String conditionColumn = wrapper.getColumn();
            Object conditionData = wrapper.getValue();
            if (conditionData instanceof List) {
                parameters = (List<Object>) conditionData;

                condition.append("(");

                List<String> params = new ArrayList<>();
                parameters.forEach(k -> params.add("?"));
                condition.append(StringUtils.join(params, COMMA));

                condition.append(")");

            } else if (Reflection.isBasicType(conditionData.getClass()) || conditionData.getClass() == String.class) {
                parameters.add(conditionData);
                condition.append("?");
            } else {
                parameters.add(Reflection.getColumnValue(conditionColumn, conditionData));
                condition.append("?");
            }

            if (StringUtils.isNotEmpty(conditionColumn)) {
                conditionSb.append(String.format(CONDITION_SQL, firstParam ? "" : wrapper.getLogic().name(), conditionColumn, wrapper.getCondition().getDescriptor(), condition.toString()));
            }

            firstParam = false;
        }


        if (StringUtils.isNotEmpty(conditionSb)) {
            sqlSb.append(String.format(WHERE_SQL, conditionSb.toString()));
        }

        return new BoundSql(sqlSb.toString(), parameters);
    }
}
