package fun.mortnon.casket.extractor.convertor;

import fun.mortnon.casket.extractor.convertor.DataConvertor;
import fun.mortnon.casket.reflect.Reflection;

import java.sql.ResultSet;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public class InstanceConvertor<T> extends BaseDataConvertor<T> {
    public InstanceConvertor(Class<T> mappedClass) {
        super(mappedClass);
    }

    @Override
    public T convert(ResultSet dbSet) {
        T instance = Reflection.instantiate(mappedClass);
        //TODO;
        return instance;
    }

}
