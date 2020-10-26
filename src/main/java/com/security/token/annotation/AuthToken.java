package com.security.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : AuthToken
 * @packageName : com.security.token.annotation
 * @description : 用来认证的token注解
 * @since : 2020/10/26
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthToken {
    /**
     * 是否必须
     *
     * @return {true(default)|false}
     */
    boolean required() default true;
}
