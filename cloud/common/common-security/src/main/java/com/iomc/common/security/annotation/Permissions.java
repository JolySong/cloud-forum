package com.iomc.common.security.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permissions {

    /**
     * 需要的权限标识
     */
    String[] value();

    /**
     * 验证模式：AND | OR
     */
    Logical logical() default Logical.AND;
}

