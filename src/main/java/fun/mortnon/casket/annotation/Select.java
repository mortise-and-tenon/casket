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
    String conditionColumn() default "";

    /**
     * 用于返回非实体的结果列名，优先级高于函数返回值
     *
     * @return
     */
    String[] selectColumns() default "";

    /**
     * 查询的表名
     *
     * @return
     */
    String table() default "";

    /**
     * 查询条件
     *
     * @return
     */
    Operator operator() default Operator.Equal;
}
