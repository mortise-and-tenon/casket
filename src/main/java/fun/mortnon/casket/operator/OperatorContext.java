package fun.mortnon.casket.operator;

import fun.mortnon.casket.annotation.CasketInvocationHandler;
import fun.mortnon.casket.annotation.Operator;
import fun.mortnon.casket.reflect.Reflection;

import javax.sql.DataSource;

/**
 * 实现用户定义的数据库操作接口生成代理实现
 *
 * @author Moon Wu
 * @date 2022/7/25
 */
public class OperatorContext {

    private DataSource dataSource;

    public OperatorContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 创建DAO代理
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> daoClass) {
        if (daoClass == null) {
            throw new NullPointerException("dao interface can't be null");
        }

        if (!daoClass.isInterface()) {
            throw new IllegalArgumentException("expected an interface to proxy, but " + daoClass);
        }

        Operator operatorAnno = daoClass.getAnnotation(Operator.class);

        if (operatorAnno == null) {
            throw new IllegalStateException("interface expected one @Operator annotation but not found");
        }

        if (dataSource == null) {
            throw new IllegalArgumentException("please set dataSource.");
        }

        CasketInvocationHandler handler = new CasketInvocationHandler(daoClass, dataSource);
        return Reflection.newProxy(daoClass, handler);
    }
}
