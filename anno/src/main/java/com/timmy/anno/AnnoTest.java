package com.timmy.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/9/21.
 */

@Target(ElementType.TYPE)//注解作用目标:方法
@Retention(RetentionPolicy.CLASS)//注解作用范围:编译期
public @interface AnnoTest {
    String value();
}
