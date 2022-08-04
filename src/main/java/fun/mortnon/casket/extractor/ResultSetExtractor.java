package fun.mortnon.casket.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 解析数据库查询结果
 *
 * @author Moon Wu
 * @date 2022/7/25
 */
public interface ResultSetExtractor {
    /**
     * 解析结果，将结果映射为List
     *
     * @param rs
     * @param returnClazz
     * @return
     * @throws SQLException
     */
    <T> List<T> extract(ResultSet rs, Class<T> returnClazz) throws SQLException;

    <T> T extractOne(ResultSet rs, Class<T> returnClazz) throws SQLException;
}
