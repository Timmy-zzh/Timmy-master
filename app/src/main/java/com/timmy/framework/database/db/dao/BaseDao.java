package com.timmy.framework.database.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.timmy.framework.database.db.annotion.DbField;
import com.timmy.framework.database.db.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by timmy1 on 17/4/23.
 * 数据库操作类：实现接口－增删改查
 * 数据库框架目标：往数据库里面保存数据
 * 实现：创建数据库（数据库位置在sd卡）－》创建数据库表－》
 * 往里面存数据－》根据存入数据对象的注解获取到对应的数据库表名称和属性对应的列名，
 */
public abstract class BaseDao<T> implements IBaseDao<T> {

    //标示是否初始化完成
    private boolean isInited = false;
    private String tableName;
    private HashMap<String, Field> cacheMap;
    private Class<T> entityClass;
    private SQLiteDatabase dataBase;

    public abstract String createTable();

    public synchronized boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        if (!isInited) {
            this.entityClass = entityClass;
            this.dataBase = sqLiteDatabase;
            //拿到表明，创建表
            if (entityClass.getAnnotation(DbTable.class) != null) {
                tableName = entityClass.getAnnotation(DbTable.class).value();
            } else {
                tableName = entityClass.getClass().getSimpleName();
            }

            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            //数据库开启了，可以创建表了
            if (!TextUtils.isEmpty(createTable())) {
                sqLiteDatabase.execSQL(createTable());
            }

            initCacheMap();
            isInited = true;
        }
        return isInited;
    }

    /**
     * 根据保存对象属性的注解，保存列名和属性字段的对应关系
     * 创建了表，就可以拿到表的列名
     */
    private void initCacheMap() {
        cacheMap = new HashMap<>();
        Cursor cursor = null;
        try {
            /**
             * 拿到表的列名
             */
            //拿到数据库表的第一行
            String sql = "select * from " + this.tableName + " limit 1,0";
            cursor = dataBase.rawQuery(sql, null);
            //拿到所有的列名
            String[] columnNames = cursor.getColumnNames();

            /**
             * 拿到所有的属性，和上面的注解－》进行比较后保存对应关系
             */
            Field[] fields = entityClass.getFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }

            for (String columnName : columnNames) {
                Field columnFilde = null;
                for (Field field : fields) {
                    String fieldName; //注解的内容
                    if (field.getAnnotation(DbField.class) != null) {
                        fieldName = field.getAnnotation(DbField.class).value();
                    } else {
                        fieldName = field.getName();
                    }
                    //比较
                    if (columnName.equals(fieldName)) {
                        columnFilde = field;
                    }
                }
                if (columnFilde != null) {
                    cacheMap.put(columnName, columnFilde);
                }
            }
        }catch (Exception e){

        }finally {
            cursor.close();
        }
    }

    /**
     * 插入一条记录
     *
     */
    @Override
    public long insert(T t) {
        HashMap<String,String> valuesMap = getValuesMap(t);
        ContentValues contentValues = getContentValues(valuesMap);
        long result = dataBase.insert(tableName, null, contentValues);
        return result;
    }

    /**
     * 根据已经保存的数据库表列名和对应的对象属性名－》拿到数据库表列名和对应属性的string类型的值
     * @param entity
     * @return
     */
    private HashMap<String, String> getValuesMap(T entity) {
        HashMap<String,String> result = new HashMap<>();
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()){
            Field columeFiled = iterator.next();
            String resultKey = null;
            String resultValue = null;
            if (columeFiled.getAnnotation(DbField.class) != null){
                resultKey = columeFiled.getAnnotation(DbField.class).value();
            }else{
                resultKey = columeFiled.getName();
            }

            try {
                resultValue = columeFiled.get(entity).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            result.put(resultKey,resultValue);
        }
        return result;
    }

    private ContentValues getContentValues(HashMap<String, String> valuesMap) {
        ContentValues contentValues = new ContentValues();
        Set<String> strings = valuesMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = valuesMap.get(key);
            if (value!= null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    @Override
    public long update(T entiy, T where) {
        return 0;
    }
}
