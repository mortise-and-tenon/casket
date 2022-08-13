package fun.mortnon.casket.extractor;

import java.sql.Date;
import java.util.Arrays;

/**
 * 数据类型与Class映射关系
 *
 * @author Moon Wu
 * @date 2022/7/28
 */
public enum DataType {

    PRIMITIVE_BOOLEAN(boolean.class),

    BOOLEAN(Boolean.class),

    PRIMITIVE_BYTE(byte.class),

    BYTE(Byte.class),

    PRIMITIVE_DOUBLE(double.class),

    DOUBLE(Double.class),

    PRIMITIVE_FLOAT(float.class),

    FLOAT(Float.class),

    PRIMITIVE_INT(int.class),

    INTEGER(Integer.class),

    PRIMITIVE_LONG(long.class),

    LONG(Long.class),

    PRIMITIVE_SHORT(short.class),

    SHORT(Short.class),

    STRING(String.class),

    DATE(Date.class),

    ENUM(Enum.class);

    private Class<?> clazz;

    DataType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getDataClass() {
        return clazz;
    }

    public static DataType valueOfClass(Class<?> clazz) {
        return Arrays.stream(DataType.values()).filter(k -> k.getDataClass() == clazz).findFirst().orElse(STRING);
    }
}
