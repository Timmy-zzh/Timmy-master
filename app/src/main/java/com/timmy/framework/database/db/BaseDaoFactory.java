package com.timmy.framework.database.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.timmy.framework.database.db.dao.BaseDao;

/**
 * Created by timmy1 on 17/4/23.
 * 数据库操作类生成工厂
 * 1.创建数据库
 * 2.初始化数据库表
 * 3.返回需要的数据库操作类
 */

public class BaseDaoFactory {

    private static BaseDaoFactory instance = new BaseDaoFactory();
    private final SQLiteDatabase sqLiteDatabase;

    private BaseDaoFactory() {
        //创建数据库－》数据库位置在sd卡
        String sqliteDbPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/timmydb.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDbPath, null);
    }

    public static BaseDaoFactory getInstance() {
        return instance;
    }

    /**
     * 获取数据库操作类
     *
     * @param daoClazz    BaseDao基类
     * @param entityClass 需要保存的数据对象
     * @param <M>         BaseDao泛型
     * @param <T>         保存对象的泛型
     * @return
     */
    public synchronized <M extends BaseDao<T>, T> M
    getDataBaseHelper(Class<M> daoClazz, Class<T> entityClass) {
        BaseDao baseDao = null;
        /**
         * 通过反射拿到BaseDao实例－》
         * 再用dao层和数据库对象，创建数据库表
         * 根据保存的对象实例获取到数据库表明，列名和已之对应的关系
         */
        try {
            baseDao = daoClazz.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return (M) baseDao;
    }

}
