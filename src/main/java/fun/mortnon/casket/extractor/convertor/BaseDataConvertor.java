package fun.mortnon.casket.extractor.convertor;

import fun.mortnon.casket.reflect.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public class BaseDataConvertor<T> implements DataConvertor<T> {
    protected Class<T> mappedClass;

    public BaseDataConvertor(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
    }

    @Override
    public T convert(ResultSet dbSet) {
        T instantiate = Reflection.instantiate(mappedClass);
        Map<String, Field> fields = Reflection.getFields(mappedClass);
        fields.forEach((k, v) -> {
            Object value = null;
            if (v.getType() == String.class) {
                try {
                    value = dbSet.getString(k);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (v.getType() == Integer.class || v.getType() == int.class) {
                try {
                    value = dbSet.getInt(k);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                Method setter = Reflection.getSetter(v, mappedClass);
                setter.invoke(instantiate, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return instantiate;
    }

    @Override
    public Class<T> getMappedClass() {
        return mappedClass;
    }
}
