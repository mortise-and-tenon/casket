package fun.mortnon.casket.extractor.fetcher;

import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Moon Wu
 * @date 2022/8/8
 */
public class DoubleFetcher implements TypeFetcher<Double> {
    @Override
    public Double fetch(ResultSet rs, String column) {
        try {
            if (StringUtils.isEmpty(column)) {
                return rs.getDouble(1);
            }
            return rs.getDouble(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
