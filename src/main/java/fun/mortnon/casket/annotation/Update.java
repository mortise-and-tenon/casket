package fun.mortnon.casket.annotation;

import java.lang.annotation.*;

/**
 * @author Moon Wu
 * @date 2022/8/5
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Update {
    /**
     * 更新对象对应的表名
     *
     * @return
     */
    String table() default "";
}
