package fun.mortnon.casket.extractor.fetcher;

import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Moon Wu
 * @date 2022/8/8
 */
public class LongFetcher implements TypeFetcher<Long> {
    @Override
    public Long fetch(ResultSet rs, String column) {
        try {
            if (StringUtils.isEmpty(column)) {
                return rs.getLong(1);
            }
            return rs.getLong(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
