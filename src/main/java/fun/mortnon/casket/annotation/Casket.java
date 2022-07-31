package fun.mortnon.casket.annotation;

import java.lang.annotation.*;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Casket {
    /**
     * 数据操作对应的表
     * 优先级低于函数上的表名声明
     *
     * @return
     */
    String table() default "";
}
