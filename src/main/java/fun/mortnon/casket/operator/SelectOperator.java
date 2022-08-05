package fun.mortnon.casket.operator;

import fun.mortnon.casket.common.Operator;
import fun.mortnon.casket.exception.DaoException;
import fun.mortnon.casket.extractor.BaseResultSetExtractor;
import fun.mortnon.casket.extractor.ResultSetExtractor;
import fun.mortnon.casket.extractor.sql.BoundSql;
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
    private final String WHERE_SQL = " WHERE %s %s %s ";
    private DataSource dataSource;
    private Method method;
    private Operator operator;

    public SelectOperator(DataSource dataSource, Method method) {
        this(dataSource, method, null);
    }

    public SelectOperator(DataSource dataSource, Method method, Operator operator) {
        this.dataSource = dataSource;
        this.method = method;
        this.operator = Optional.ofNullable(operator).orElse(Operator.Equal);
    }

    public <T> List<T> select(String conditionColumn, Object conditionData, Class<T> returnClazz, String tableName) throws SQLException {
        return select(conditionColumn, conditionData, null, returnClazz, tableName);
    }

    public <T> List<T> select(String conditionColumn, Object conditionData, List<String> selectColumns, Class<T> returnClazz,
                              String tableName) throws SQLException {
        BoundSql boundSql = analyze(conditionColumn, conditionData, selectColumns, returnClazz, tableName);
        ResultSet resultSet = new DatabaseExecutor(dataSource).select(boundSql);
        ResultSetExtractor extractor = new BaseResultSetExtractor();
        return extractor.extract(resultSet, returnClazz);
    }

    public <T> T selectOne(String conditionColumn, Object conditionData, List<String> selectColumns, Class<T> returnClazz,
                           String tableName) throws SQLException {
        BoundSql boundSql = analyze(conditionColumn, conditionData, selectColumns, returnClazz, tableName);
        ResultSet resultSet = new DatabaseExecutor(dataSource).select(boundSql);

        if (null == resultSet) {
            return null;
        }

        ResultSetExtractor extractor = new BaseResultSetExtractor();
        return extractor.extractOne(resultSet, returnClazz);
    }

    private <T> BoundSql analyze(String conditionColumn, Object conditionData, List<String> selectColumns, Class<T> returnClazz,
                                 String tableName) {
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

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(SELECT_SQL, fieldsSql, tableName));

        StringBuilder condition = new StringBuilder();

        List<Object> parameters = new ArrayList<>();
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
            sb.append(String.format(WHERE_SQL, conditionColumn, operator.getDescriptor(), condition.toString()));
        }

        return new BoundSql(sb.toString(), parameters);
    }
}
