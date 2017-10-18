package com.timmy.framework.retrofit2;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by admin on 2017/9/23.
 * 请求URL注解
 */

@Target(METHOD)
@Retention(RUNTIME)
public @interface ReqUrl {
    //    String value();
    String reqUrl() default "";
}
