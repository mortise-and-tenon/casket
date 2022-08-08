package fun.mortnon.casket.extractor.fetcher;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Moon Wu
 * @date 2022/8/8
 */
public class ByteFetcher implements TypeFetcher<Byte> {
    @Override
    public Byte fetch(ResultSet rs, String column) {
        try {
            return rs.getByte(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
