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

    PRIMITIVE_BOOLEAN(boolean.class, (rs, column) -> {
        try {
            return rs.getBoolean(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    BOOLEAN(Boolean.class, (rs, column) -> {
        try {
            return rs.getBoolean(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    PRIMITIVE_BYTE(byte.class, (rs, column) -> {
        try {
            return rs.getByte(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    BYTE(Byte.class, (rs, column) -> {
        try {
            return rs.getByte(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    PRIMITIVE_DOUBLE(double.class, (rs, column) -> {
        try {
            return rs.getDouble(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    DOUBLE(Double.class, (rs, column) -> {
        try {
            return rs.getDouble(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    PRIMITIVE_FLOAT(float.class, (rs, column) -> {
        try {
            return rs.getFloat(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    FLOAT(Float.class, (rs, column) -> {
        try {
            return rs.getFloat(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    PRIMITIVE_INT(int.class, (rs, column) -> {
        try {
            return rs.getInt(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    INTEGER(Integer.class, (rs, column) -> {
        try {
            if (StringUtils.isEmpty(column)) {
                return rs.getInt(1);
            }

            return rs.getInt(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    PRIMITIVE_LONG(long.class, (rs, column) -> {
        try {
            return rs.getLong(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    LONG(Long.class, (rs, column) -> {
        try {
            return rs.getLong(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    PRIMITIVE_SHORT(short.class, (rs, column) -> {
        try {
            return rs.getShort(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    SHORT(Short.class, (rs, column) -> {
        try {
            return rs.getShort(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    STRING(String.class, (rs, column) -> {
        try {
            if (StringUtils.isEmpty(column)) {
                return rs.getString(1);
            }
            return rs.getString(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    DATE(Date.class, (rs, column) -> {
        try {
            return rs.getDate(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }),

    ENUM(Enum.class, (rs, column) -> {
        try {
            return rs.getString(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });

    private Class<?> clazz;
    private BiFunction<ResultSet, String, Object> func;

    TypeMapper(Class<?> clazz, BiFunction<ResultSet, String, Object> func) {
        this.clazz = clazz;
        this.func = func;
    }

    /**
     * 按类型获取对应的列数据
     *
     * @param clazz
     * @param rs
     * @param column
     * @return
     */
    public static Object grab(Class<?> clazz, ResultSet rs, String column) {
        return Arrays.stream(TypeMapper.values()).filter(k -> k.clazz == clazz).findFirst().orElse(STRING)
                .grabData(rs, column);
    }

    public static Object grab(Class<?> clazz, ResultSet rs) {
        return grab(clazz, rs, null);
    }

    private Object grabData(ResultSet rs, String column) {
        if (null == this.func) {
            return null;
        }

        return this.func.apply(rs, column);
    }

}
