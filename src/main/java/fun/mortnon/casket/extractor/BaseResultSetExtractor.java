package fun.mortnon.casket.extractor;

import fun.mortnon.casket.extractor.convertor.AbstractBaseRowConvertor;
import fun.mortnon.casket.extractor.convertor.InstanceRowConvertor;
import fun.mortnon.casket.extractor.convertor.RowConvertor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析数据为List
 *
 * @author Moon Wu
 * @date 2022/7/25
 */
public class BaseResultSetExtractor<T> implements ResultSetExtractor {
    public BaseResultSetExtractor() {

    }

    @Override
    public <T> List<T> extract(ResultSet rs, Class<T> clazz) throws SQLException {
        List<T> list = new ArrayList<>();
        RowConvertor<T> convertor = new InstanceRowConvertor<>(clazz);
        while (rs.next()) {
            T data = convertor.convert(rs);
            list.add(data);
        }
        return list;
    }
}
