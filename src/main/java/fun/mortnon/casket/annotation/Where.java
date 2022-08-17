package fun.mortnon.casket.annotation;

import fun.mortnon.casket.common.Condition;
import fun.mortnon.casket.common.Logic;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {
    /**
     * 与前一个条件的逻辑关系
     *
     * @return
     */
    Logic logic() default Logic.AND;

    /**
     * 条件匹配的列名
     *
     * @return
     */
    String column();

    /**
     * 匹配条件
     *
     * @return
     */
    Condition operator() default Condition.Equal;
}
