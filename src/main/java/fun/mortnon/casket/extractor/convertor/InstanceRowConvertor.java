package fun.mortnon.casket.extractor.convertor;

import fun.mortnon.casket.extractor.TypeMapper;
import fun.mortnon.casket.reflect.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Map;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public class InstanceRowConvertor<T> extends AbstractBaseRowConvertor<T> {
    public InstanceRowConvertor(Class<T> mappedClass) {
        super(mappedClass);
    }

    @Override
    public T convert(ResultSet resultSet) {
        T instance = Reflection.instantiate(mappedClass);
        Map<String, Field> fields = Reflection.getFields(mappedClass);
        fields.forEach((k, v) -> {
            Object value = TypeMapper.grab(v.getType(), resultSet, k);
            if (v.getType().isEnum() && value.getClass() == String.class) {
                value = Enum.valueOf((Class<Enum>)v.getType(), (String)value);
            }

            try {
                Method setter = Reflection.getSetter(v, mappedClass);
                setter.invoke(instance, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return instance;
    }

}
