package com.timmy.framework.database.normal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.timmy.library.util.Toast;
import com.timmy.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/21.
 * 数据库操作类
 */
public class DbDao {
    private Context context;
    private TimmyDataBaseHelper dataBaseHelper;

    public DbDao(Context context) {
        this.context = context;
        dataBaseHelper = new TimmyDataBaseHelper(context);
    }

    /**
     * 使用Android提供的API方式进行插入
     * 往数据库表中插入一条记录
     * 拿到数据库->使用ContentValues往里面添加
     */
    public void insert(Person person){
        /**
         * getWritableDatabase()方法以读写方式打开数据库,一旦数据库的磁盘空间满了,数据库只能读不能写,
         * 倘若使用getWritableDatabase()打开数据库就会出错
         */
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        /**
         * getReadableDatabase()方法以读写方式打开数据库,如果数据库的磁盘空间满了,就会打开失败,
         * 当打开失败后会继续以只读方式打开数据库
         */
//        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",person.getName());
        contentValues.put("age",person.getAge());
//        contentValues.put("phone",person.getPhone());

        /**
         * person 表名
         *
         */
        long result = writableDatabase.insert("person", null, contentValues);
        LogUtils.d(result+"");
    }

    /**
     * 插入一条记录:方式二使用sqlite语句操作
     * @param person
     */
    public void insert2(Person person){
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        writableDatabase.execSQL("insert into person (name,age) values (?,?)",new Object[]{person.getName(),person.getAge()});
        writableDatabase.close();
    }

    /**
     * 删除一条记录
     * @param age
     */
    public void delete(Integer age){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int result = db.delete("person", "age=?", new String[]{age.toString()});
        LogUtils.d(result+"");
    }

    public void delete2(Integer age){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.execSQL("delete from person where age=?",new Object[]{age});
        db.close();
    }

    /**
     * 更新一个数据
     * @param person
     */
    public void updata(Person person){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",person.getName());
        values.put("age",person.getAge());

        int result = db.update("person", values, "age=?", new String[]{person.getAge() + ""});
        LogUtils.d(result+"");
    }
    public void updata2(Person person){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.execSQL("update person set name=?,age=? where age=?",new Object[]{person.getName(),person.getAge(),person.getAge()});
        db.close();
    }

    /**
     * 根据年龄获取记录
     * @param age
     * @return
     */
    public Person find(int age){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        Cursor cursor = db.query("person", null, "age=?", new String[]{age + ""}, null, null, null, null);

        if (cursor.moveToFirst()){//判断数据是否存在
            int perAge = cursor.getInt(cursor.getColumnIndex("age"));
            String name =cursor.getString(cursor.getColumnIndex("name"));
            cursor.close();
            return new Person(name,age,null);
        }else{
            cursor.close();
            LogUtils.d("数据库不存在该数据");
            Toast.toastShort("数据库不存在该数据");
            return null;
        }
    }


    public Person find2(int age){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * person where age=?",new String[]{age+""});

        if (cursor.moveToFirst()){//判断数据是否存在
            int perAge = cursor.getInt(cursor.getColumnIndex("age"));
            String name =cursor.getString(cursor.getColumnIndex("name"));
            cursor.close();
            return new Person(name,age,null);
        }else{
            cursor.close();
            LogUtils.d("数据库不存在该数据");
            Toast.toastShort("数据库不存在该数据");
            return null;
        }
    }

    /**
     * 获取分页数据
     * @param offset  从第条数据开始获取
     * @param pageCount 获取的数据
     * @return
     */
    public List<Person> findPageData(int offset,int pageCount){
        List<Person> personList = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
//        db.query("person",null,null,null,null,"age asc",offset+","+pageCount);

        Cursor cursor = db.rawQuery("select * from person order by persionId asc limit ?,?",
                new String[]{String.valueOf(offset), String.valueOf(pageCount)});
        while (cursor.moveToNext()){
            int perAge = cursor.getInt(cursor.getColumnIndex("age"));
            String name =cursor.getString(cursor.getColumnIndex("name"));
            personList.add(new Person(name,perAge,null));
        }
        cursor.close();
        return personList;
    }

    /**
     * 获取数据库条目
     * @return
     */
    public long getCount(){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
//        db.query("person",new String[]{"count(*)"},null,null,null,null,null);
        Cursor cursor = db.rawQuery("select count(*) from person", null);
        cursor.moveToNext();
        long result = cursor.getLong(0);
        cursor.close();
        return result;
    }


    public List<Person> findAll(){
        List<Person> personList = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
//        db.query("person",null,null,null,null,"age asc",offset+","+pageCount);

        Cursor cursor = db.rawQuery("select * from person order by persionId asc limit ?,?",
                new String[]{String.valueOf(0), String.valueOf(getCount())});
        while (cursor.moveToNext()){
            int perAge = cursor.getInt(cursor.getColumnIndex("age"));
            String name =cursor.getString(cursor.getColumnIndex("name"));
            personList.add(new Person(name,perAge,null));
        }
        cursor.close();
        return personList;
    }
}
