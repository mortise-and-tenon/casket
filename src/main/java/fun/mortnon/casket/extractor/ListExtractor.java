package fun.mortnon.casket.extractor;

import fun.mortnon.casket.extractor.convertor.BaseDataConvertor;
import fun.mortnon.casket.extractor.convertor.DataConvertor;

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
public class ListExtractor implements ResultSetExtractor {
    private DataConvertor dataConvertor;


    public ListExtractor(DataConvertor dataConvertor) {
        this.dataConvertor = dataConvertor;
    }

    @Override
    public <T> List<T> extract(ResultSet rs, Class<T> clazz) throws SQLException {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            T data = new BaseDataConvertor<T>(clazz).convert(rs);
            list.add(data);
        }
        return list;
    }
}
