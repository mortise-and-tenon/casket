package fun.mortnon.casket.extractor.sql;

import fun.mortnon.casket.common.Condition;
import fun.mortnon.casket.common.Logic;

/**
 * SQL操作的条件包装器
 *
 * @author Moon Wu
 * @date 2022/8/17
 */
public class ConditionWrapper {
    /**
     * 数据库表字段
     */
    private String column;
    /**
     * 条件运算符
     */
    private Condition condition;
    /**
     * 匹配的数据
     */
    private Object value;

    /**
     * 与前一个条件的逻辑关系
     */
    private Logic logic;

    public ConditionWrapper(String column, Condition condition, Object value, Logic logic) {
        this.column = column;
        this.condition = condition;
        this.value = value;
        this.logic = logic;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }
}
