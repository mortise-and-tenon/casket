package fun.mortnon.casket.extractor.convertor;

import java.sql.ResultSet;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public interface DataConvertor<T>{
    T convert(ResultSet dbSet);

    Class<T> getMappedClass();
}
