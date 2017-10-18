package com.timmy.framework.retrofit2;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by admin on 2017/9/23.
 * 请求类型的注解
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface ReqType {

    /**
     * ReqTypeEnum 使用注解时返回的类型
     * reqType() 使用注解的key值
     * @return
     */
    ReqTypeEnum reqType() default ReqTypeEnum.POST;

    enum ReqTypeEnum{GET,POST,PUT,DELETE}
}
