package org.cny.yurayura.annotation;

import java.lang.annotation.*;

/**
 * 防止重复提交 annotation
 *
 * @author CNY
 * @since 2019-11-14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PreventRepeatSubmit {
    // prefix为本地锁的key的前缀
    String prefix() default "prefix:";
}
