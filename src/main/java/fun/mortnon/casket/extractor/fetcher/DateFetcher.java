package fun.mortnon.casket.extractor.fetcher;

import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Moon Wu
 * @date 2022/8/8
 */
public class DateFetcher implements TypeFetcher<Date> {
    @Override
    public Date fetch(ResultSet rs, String column) {
        try {
            if (StringUtils.isEmpty(column)) {
                return rs.getDate(1);
            }
            return rs.getDate(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
