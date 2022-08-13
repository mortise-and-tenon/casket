package fun.mortnon.casket.extractor.convertor;

import java.sql.ResultSet;

/**
 * 数据库行与数据的转换器接口
 *
 * @author Moon Wu
 * @date 2022/7/25
 */
public interface RowConvertor<T> {
    /**
     * 将数据表行数据转换为对象数据
     *
     * @param dbSet
     * @return
     */
    T convert(ResultSet dbSet);

    /**
     * 对象对应的类或类型
     *
     * @return
     */
    Class<T> getMappedClass();
}
