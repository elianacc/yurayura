package org.cny.yurayura.annotation;

import java.lang.annotation.*;

/**
 * 防止重复提交 annotation
 *
 * @author CNY
 * @date 2019年11月14日 2:13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PreventRepeatSubmit {
    // prefix为本地锁的key的前缀
    String prefix() default "prefix:";
}
