package com.timmy.framework.annotationRuntime.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/3/31.
 * 为view的点击事件设置注解的注解
 */
@Target(ElementType.ANNOTATION_TYPE) //注解应用的地方-注解的注解使用
@Retention(RetentionPolicy.RUNTIME) //注解的生命周期
public @interface EventBase {

    //设置注解的参数
    Class<?> listenerType(); //监听器的类型->View.OnClickListener 接口类型

    String listenerSetter();//设置监听的方法->setOnClickLisetener

    String methodName(); //接口回调的方法->onClick()

}
