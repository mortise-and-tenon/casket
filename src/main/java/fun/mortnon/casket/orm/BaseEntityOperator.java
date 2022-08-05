package fun.mortnon.casket.orm;

import fun.mortnon.casket.annotation.Select;

import java.util.List;

/**
 * @author Moon Wu
 * @date 2022/8/4
 */
public interface BaseEntityOperator<T> {
    /**
     * 获取所有数据
     *
     * @return
     */
    @Select
    List<T> selectAll();
}
