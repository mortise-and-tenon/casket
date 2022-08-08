package fun.mortnon.casket.extractor.fetcher;

import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Moon Wu
 * @date 2022/8/8
 */
public class BooleanFetcher implements TypeFetcher<Boolean> {
    @Override
    public Boolean fetch(ResultSet rs, String column) {
        try {
            if (StringUtils.isEmpty(column)) {
                return rs.getBoolean(1);
            }
            return rs.getBoolean(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
