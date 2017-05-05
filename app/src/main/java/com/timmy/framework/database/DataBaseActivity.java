package com.timmy.framework.database;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.framework.annotationsFramework.ViewInjectUtils;
import com.timmy.framework.annotationsFramework.annotations.ContentView;
import com.timmy.framework.annotationsFramework.annotations.ViewInject;
import com.timmy.framework.database.db.BaseDaoFactory;
import com.timmy.framework.database.normal.DbDao;
import com.timmy.framework.database.normal.Person;
import com.timmy.library.util.Toast;

import java.util.List;

/**
 * 数据库操作 SQLite
 */
@ContentView(R.layout.activity_data_base)
public class DataBaseActivity extends BaseActivity {

    @ViewInject(R.id.et_name)
    EditText etName;
    @ViewInject(R.id.et_age)
    EditText etAge;
    @ViewInject(R.id.tv_content)
    TextView tvContent;

    DbDao dbDao;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);

//        dbDao = new DbDao(this);
//
//        showDataBaseData();


        userDao = BaseDaoFactory.getInstance().getDataBaseHelper(UserDao.class, User.class);

    }

    private void showDataBaseData() {
        tvContent.setText(dbDao.findAll().toString());
    }

    /**
     * 数据库插入
     *
     * @param view
     */
    public void insert(View view) {
//        String name = etName.getText().toString();
//        String age = etAge.getText().toString();
//        int ageInt = Integer.parseInt(age);
//        dbDao.insert(new Person(name, ageInt, null));
//
//        tvContent.setText(dbDao.findAll().toString());

        for (int i = 0; i < 20; i++) {
            User user;
            if (i % 2 == 0) {
                user = new User("timmy", "111-"+i);
            } else {
                user = new User("timmy" + i, "111-" + i);
            }
            userDao.insert(user);
        }
    }

    public void delete(View view) {
//        String age = etAge.getText().toString();
//        int ageInt = Integer.parseInt(age);
//        dbDao.delete(ageInt);
//        tvContent.setText(dbDao.findAll().toString());

        User user = new User();
        user.setName("timmy" + 1);
        long count = userDao.delete(user);
        Toast.toastShort("count:" + count);
    }

    public void update(View view) {
//        String name = etName.getText().toString();
//        String age = etAge.getText().toString();
//        int ageInt = Integer.parseInt(age);
//        dbDao.updata(new Person(name, ageInt, null));
//        tvContent.setText(dbDao.findAll().toString());

        User newUser = new User("xxx", "12545");
        User where = new User();
        where.setName("timmy" + 7);
        long count = userDao.update(newUser, where);
        Toast.toastShort("count:" + count);
    }

    public void search(View view) {
//        String age = etAge.getText().toString();
//        int ageInt = Integer.parseInt(age);
//
//        tvContent.setText("条件:" + dbDao.find(ageInt).toString() + ",全部:" + dbDao.findAll().toString());

        User user = new User();
        user.setName("timmy");
        List<User> list = userDao.query(user);
        tvContent.setText("条件,全部:" + list.toString());

    }

}
