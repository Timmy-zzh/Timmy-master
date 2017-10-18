package com.timmy.framework.annotationRuntime.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/3/30.
 * 用于findviewbyid方法,返回对象属性的注解
 */

@Target(ElementType.FIELD) //注解在成员变量上使用
//@Target(ElementType.METHOD) //注解在成员变量上使用
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {

    int value();//
}
