package fun.mortnon.casket.annotation;


import fun.mortnon.casket.common.Operator;

import java.lang.annotation.*;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Select {
    /**
     * 用作查询的列名
     *
     * @return
     */
    String column();

    /**
     * 操作的实体类
     *
     * @return
     */
    Class<?> entity();

    /**
     * 查询条件
     *
     * @return
     */
    Operator operator() default Operator.Equal;
}
