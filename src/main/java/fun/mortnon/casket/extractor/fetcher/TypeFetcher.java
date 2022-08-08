package fun.mortnon.casket.extractor.fetcher;

import java.sql.ResultSet;

/**
 * @author Moon Wu
 * @date 2022/8/5
 */
public interface TypeFetcher<T> {

    /**
     * 获取指定对象对应的数据
     *
     * @param rs
     * @param column
     * @return
     */
    T fetch(ResultSet rs, String column);
}
