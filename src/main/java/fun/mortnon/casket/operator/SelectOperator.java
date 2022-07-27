package fun.mortnon.casket.operator;

import fun.mortnon.casket.common.Operator;
import fun.mortnon.casket.extractor.ListExtractor;
import fun.mortnon.casket.extractor.ResultSetExtractor;
import fun.mortnon.casket.extractor.convertor.BaseDataConvertor;
import fun.mortnon.casket.extractor.sql.BoundSql;
import fun.mortnon.casket.reflect.Reflection;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
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
    private Operator operator;

    public SelectOperator(DataSource dataSource) {
        this(dataSource, null);
    }

    public SelectOperator(DataSource dataSource, Operator operator) {
        this.dataSource = dataSource;
        this.operator = Optional.ofNullable(operator).orElse(Operator.Equal);
    }

    public <T> List<T> select(String column, Object data, Class<T> clazz) throws SQLException {
        BoundSql boundSql = analyze(column, data, clazz);
        ResultSet resultSet = new DatabaseExecutor(dataSource).select(boundSql);
        ResultSetExtractor extractor = new ListExtractor(new BaseDataConvertor(clazz));
        return extractor.extract(resultSet, clazz);
    }

    private <T> BoundSql analyze(String column, Object data, Class<T> clazz) {
        Map<String, Type> fields = Reflection.getColumns(clazz);
        String fieldsSql = StringUtils.join(fields.keySet(), COMMA);
        String tableName = Reflection.getTableName(clazz);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(SELECT_SQL, fieldsSql, tableName));

        StringBuilder condition = new StringBuilder();

        List<Object> parameters = new ArrayList<>();
        if (data instanceof List) {
            parameters = (List<Object>) data;
            condition.append("(");

            List<String> params = new ArrayList<>();
            parameters.forEach(k -> params.add("?"));

            condition.append(StringUtils.join(params, COMMA));

            condition.append(")");

        } else if (data.getClass() instanceof Type) {
            parameters.add(data);
            condition.append("?");
        } else {
            parameters.add(Reflection.getColumnValue(column, clazz));
            condition.append("?");
        }

        sb.append(String.format(WHERE_SQL, column, operator.getDescriptor(), condition.toString()));

        return new BoundSql(sb.toString(), parameters);
    }
}
