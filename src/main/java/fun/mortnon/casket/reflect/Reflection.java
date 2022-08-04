package fun.mortnon.casket.reflect;


import fun.mortnon.casket.exception.InstanceException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.*;
import java.util.*;

import static fun.mortnon.casket.common.Constants.FIELD_TYPE;

/**
 * @author Moon Wu
 */
public class Reflection {

    public static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler) {
        if (!interfaceType.isInterface()) {
            throw new IllegalArgumentException("expected an interface to proxy, but " + interfaceType);
        }

        Object object = Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                handler);
        return interfaceType.cast(object);
    }

    public static Map<String, Type> getColumns(Class<?> clazz) {
        Map<String, Type> fieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String columnName;
            boolean isColumn = field.isAnnotationPresent(Column.class);
            if (isColumn) {
                Column columnAnno = field.getAnnotation(Column.class);
                columnName = columnAnno.name();
            } else {
                columnName = field.getName();
            }
            fieldMap.put(columnName, field.getType());
        }
        return fieldMap;
    }

    public static String getTableName(Class<?> clazz) {
        boolean isTable = clazz.isAnnotationPresent(Table.class);
        if (isTable) {
            Table tableAnno = clazz.getAnnotation(Table.class);
            if (StringUtils.isNotEmpty(tableAnno.name())) {
                return tableAnno.name();
            }
        }
        return clazz.getSimpleName();
    }

    public static Field getField(String name, Class<?> clazz) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                boolean isColumn = field.isAnnotationPresent(Column.class);
                if (isColumn) {
                    Column columnAnno = field.getAnnotation(Column.class);
                    if (name.equals(columnAnno.name())) {
                        return field;
                    }
                }
            }
        }
        return null;
    }

    public static Map<String, Field> getFields(Class<?> clazz) {
        Map<String, Field> map = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            map.put(field.getName(), field);
        }
        return map;
    }

    public static Method getSetter(Field field, Class<?> clazz) {
        try {
            return clazz.getMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Object getColumnValue(String column, Object obj) {
        try {
            Field field = getField(column, obj.getClass());
            if (null == field) {
                throw new RuntimeException("parameter not contains value for sql.");
            }
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("parameter not contains value for sql.");
        }
    }

    public static <T> T instantiate(Class<T> clazz) throws InstanceException {
        if (clazz.isInterface()) {
            throw new InstanceException(clazz, "specified class is an interface");
        }

        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new InstanceException(clazz, "Is it an abstract class?", e);
        } catch (IllegalAccessException e) {
            throw new InstanceException(clazz, "Is the constructor accessible?", e);
        }
    }

    /**
     * 判定当前类型是否为基本数据类型
     *
     * @param clazz
     * @return
     */
    public static boolean isBasicType(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }

        try {
            Field field = clazz.getField(FIELD_TYPE);
            return ((Class) field.get(null)).isPrimitive();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
