package com.timmy.framework.annotationsFramework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/3/30.
 * 定义注解
 */

@Target(ElementType.TYPE) //表示注解作用在类上面
@Retention(RetentionPolicy.RUNTIME)//表示注解的生命周期为运行期
public @interface ContentView {
    int value(); // 返回基本数据类型
}
