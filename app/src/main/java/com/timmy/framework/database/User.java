package com.timmy.framework.database;

import com.timmy.framework.database.db.annotion.DbField;
import com.timmy.framework.database.db.annotion.DbTable;

/**
 * Created by timmy1 on 17/4/23.
 */

@DbTable("tb_user")
public class User {

    @DbField("name")
    private String name;

    @DbField("password")
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
