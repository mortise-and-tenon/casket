package fun.mortnon.casket.orm;

import fun.mortnon.casket.operator.OperatorContext;

import javax.sql.DataSource;

/**
 * @author Moon Wu
 * @date 2022/8/5
 */
public class OperatorManager {
    private OperatorContext operatorContext;

    public OperatorManager(DataSource dataSource) {
        this.operatorContext = new OperatorContext(dataSource);
    }

    public <T extends BaseEntityOperator> T instance(Class<T> operatorClass) {
        return operatorContext.create(operatorClass);
    }
}
