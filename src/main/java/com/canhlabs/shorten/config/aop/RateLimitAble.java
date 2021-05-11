package com.canhlabs.shorten.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RateLimitAble {
    /**
     *
     * pass by millisecond
     */
    int timeLimit() default 0; // in case not set, will using from configure file

    short countLimit() default 0; // in case not set, will using from configure file
}
