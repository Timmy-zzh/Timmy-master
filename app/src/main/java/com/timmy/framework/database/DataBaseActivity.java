package com.timmy.framework.database;

import android.os.Bundle;
import android.view.View;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.framework.annotationsFramework.ViewInjectUtils;
import com.timmy.framework.annotationsFramework.annotations.ContentView;

/**
 * 数据库操作 SQLite
 */
@ContentView(R.layout.activity_data_base)
public class DataBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);


    }

    /**
     * 数据库插入
     * @param view
     */
    public void insert(View view){


    }

}
