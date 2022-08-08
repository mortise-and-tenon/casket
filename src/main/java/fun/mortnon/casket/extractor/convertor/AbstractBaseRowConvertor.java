package fun.mortnon.casket.extractor.convertor;

import fun.mortnon.casket.extractor.TypeFetcherFactory;

import java.sql.ResultSet;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public abstract class AbstractBaseRowConvertor<T> implements RowConvertor<T> {
    protected Class<T> mappedClass;
    protected TypeFetcherFactory typeFetcherFactory;

    public AbstractBaseRowConvertor(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        typeFetcherFactory = new TypeFetcherFactory();
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

    /**
     * 获取指定类型对应的数据
     *
     * @param clazz
     * @param resultSet
     * @param column
     * @return
     */
    protected Object fetch(Class<?> clazz, ResultSet resultSet, String column) {
        return typeFetcherFactory.build(clazz).fetch(resultSet, column);
    }
}
