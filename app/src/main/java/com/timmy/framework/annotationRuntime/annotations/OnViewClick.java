package com.timmy.framework.annotationRuntime.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/3/31.
 * Activity方法上点击事件的方法注解
 */
@Target(ElementType.METHOD) //注解作用于方法
@Retention(RetentionPolicy.RUNTIME) //注解生命周期为运行时
@EventBase(listenerType = View.OnClickListener.class, listenerSetter = "setOnClickListener", methodName = "onClick")
public @interface OnViewClick {

    //注解设置的参数-多个view的id
    int[] value();

}
