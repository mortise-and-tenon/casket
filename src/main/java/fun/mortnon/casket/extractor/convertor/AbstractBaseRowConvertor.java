package fun.mortnon.casket.extractor.convertor;

import java.sql.ResultSet;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public abstract class AbstractBaseRowConvertor<T> implements RowConvertor<T> {
    protected Class<T> mappedClass;

    public AbstractBaseRowConvertor(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
    }

    /**
     * 表行转换为对象数据
     *
     * @param resultSet
     * @return
     */
    @Override
    public abstract T convert(ResultSet resultSet);

    @Override
    public Class<T> getMappedClass() {
        return mappedClass;
    }
}
