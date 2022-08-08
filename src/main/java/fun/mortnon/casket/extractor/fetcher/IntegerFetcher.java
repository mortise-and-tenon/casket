package fun.mortnon.casket.extractor.fetcher;

import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Moon Wu
 * @date 2022/8/8
 */
public class IntegerFetcher implements TypeFetcher<Integer> {
    @Override
    public Integer fetch(ResultSet rs, String column) {
        try {
            if (StringUtils.isEmpty(column)) {
                return rs.getInt(1);
            }
            return rs.getInt(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
