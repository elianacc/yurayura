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
public @interface PreventRepeatSubmit {
    String value() default "";
}
