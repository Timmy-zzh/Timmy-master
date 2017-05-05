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

    public User(){}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
