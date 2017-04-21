package com.timmy.framework.database.normal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.timmy.util.LogUtils;


/**
 * Created by admin on 2017/4/20.
 * 数据库帮助类,--用于生产数据库
 */
public class TimmyDataBaseHelper extends SQLiteOpenHelper {


    private static final String name = "timmyDb.db";
    private static final int version = 1;


    public TimmyDataBaseHelper(Context context) {
        /**
         *
         * @param context
         * @param name  数据库名称
         * @param factory 游标实例工厂,null代表使用默认工厂类
         * @param version 数据库版本
         */
        super(context, name, null, version);
    }



    /**
     * 创建数据库表 person
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.d("SQLiteDatabase 创建数据库表");
        db.execSQL("create table if not exists person (persionId integer primary key autoincrement, name varchar(20),age integer)");
    }

    /**
     * 数据库更新时调用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.d("SQLiteDatabase 更新数据库表 oldVersion:"+oldVersion+",newVersion:"+newVersion);
        //新增一个字段
        db.execSQL("alter table person add phone varchar(12)");
    }
}
