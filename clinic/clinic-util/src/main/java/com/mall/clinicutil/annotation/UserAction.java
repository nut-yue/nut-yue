package com.mall.clinicutil.annotation;


import java.lang.annotation.*;

/**
 * @author nut-yue
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAction {

    String action() default "";
}
