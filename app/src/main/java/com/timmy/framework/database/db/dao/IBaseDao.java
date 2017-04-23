package com.timmy.framework.database.db.dao;

/**
 * Created by timmy1 on 17/4/23.
 * BaseDao  数据库操作类接口：方法包括 增删改查
 */
public interface IBaseDao<T> {

    long insert(T t);

    long update(T entiy,T where);

//    long delete(T t);

}
