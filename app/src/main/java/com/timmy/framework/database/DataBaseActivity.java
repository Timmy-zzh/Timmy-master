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

        User user = new User("timmy","123456");

        userDao.insert(user);
    }

    public void delete(View view) {
        String age = etAge.getText().toString();
        int ageInt = Integer.parseInt(age);
        dbDao.delete(ageInt);
        tvContent.setText(dbDao.findAll().toString());
    }

    public void update(View view) {
        String name = etName.getText().toString();
        String age = etAge.getText().toString();
        int ageInt = Integer.parseInt(age);
        dbDao.updata(new Person(name, ageInt, null));
        tvContent.setText(dbDao.findAll().toString());
    }

    public void search(View view) {
        String age = etAge.getText().toString();
        int ageInt = Integer.parseInt(age);

        tvContent.setText("条件:" + dbDao.find(ageInt).toString() + ",全部:" + dbDao.findAll().toString());
    }

}
