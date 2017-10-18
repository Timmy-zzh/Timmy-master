package com.timmy.framework.retrofit2;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by admin on 2017/9/23.
 * 请求参数的注解
 */

@Target(PARAMETER)
@Retention(RUNTIME)
public @interface ReqParam {
    String value();
}
