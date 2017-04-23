package com.timmy.framework.database.db.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by timmy1 on 17/4/23.
 * 描述存入对象的属性名和对应的数据库表列名关系
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbField {
    String value();
}
