package com.timmy.framework.annotationsFramework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/3/30.
 */

@Target(ElementType.METHOD)  //表示该注解用于什么地方
@Retention(RetentionPolicy.RUNTIME)//定义该注解的生命周期
public @interface TestAnno {
    /**
     * Annotations只支持基本类型、String及枚举类型。注释中所有的属性被定义成方法，并允许提供默认值
     */
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }
    public enum Status{
        STARTED,
        NOT_STARTED
    }

    String author()  default "Timmy";
    Priority priority() default Priority.LOW;
    Status status() default Status.NOT_STARTED;
}
