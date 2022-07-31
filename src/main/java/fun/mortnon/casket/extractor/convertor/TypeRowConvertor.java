package fun.mortnon.casket.extractor.convertor;

import fun.mortnon.casket.extractor.TypeMapper;

import java.sql.ResultSet;

/**
 * 将数据库数据转换为基本类型
 *
 * @param <T>
 * @author Moon Wu
 */
public class TypeRowConvertor<T> extends AbstractBaseRowConvertor<T> {
    public TypeRowConvertor(Class<T> mappedClass) {
        super(mappedClass);
    }

    @Override
    public T convert(ResultSet resultSet) {
        return (T) TypeMapper.grab(mappedClass, resultSet);
    }
}
