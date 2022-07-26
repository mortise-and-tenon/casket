package fun.mortnon.casket.annotation;


import fun.mortnon.casket.common.Condition;

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
     * 用于返回非实体的结果列名，优先级高于函数返回值
     *
     * @return
     */
    String[] columns() default "";

    /**
     * 查询的表名
     *
     * @return
     */
    String from() default "";

    /**
     * 查询条件
     *
     * @return
     */
    Condition operator() default Condition.Equal;
}
