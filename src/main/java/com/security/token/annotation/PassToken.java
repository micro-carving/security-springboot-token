package com.security.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : PassToken
 * @packageName : com.security.token.annotation
 * @description : 认证通过注解
 * @since : 2020/10/28
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}