package fun.mortnon.casket.extractor.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Moon Wu
 * @date 2022/7/26
 */
@Data
@AllArgsConstructor
public class BoundSql {
    private String sql;
    private List<Object> parameters;
}
