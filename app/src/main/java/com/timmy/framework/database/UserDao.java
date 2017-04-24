package com.timmy.framework.database;

import com.timmy.framework.database.db.dao.BaseDao;

/**
 * Created by timmy1 on 17/4/23.
 */

public class UserDao extends BaseDao {

    /**
     * 创建数据库表
     * @return
     */
    @Override
    public String createTable() {
        return "create table if not exists tb_user(name varchar(20),password varchar(10))";
    }
}
