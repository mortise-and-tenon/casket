package fun.mortnon.casket.extractor;

import com.mysql.cj.protocol.Resultset;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Moon Wu
 * @date 2022/7/28
 */
public enum TypeMapper {

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

    TypeMapper(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getDataClass() {
        return clazz;
    }

    public static TypeMapper valueOfClass(Class<?> clazz) {
        return Arrays.stream(TypeMapper.values()).filter(k -> k.getDataClass() == clazz).findFirst().orElse(STRING);
    }
}
