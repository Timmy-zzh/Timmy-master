package com.timmy.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.timmy.R;
import com.timmy.ui.adapter.CustomAdapter;
import com.timmy.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.cl_root)
    CoordinatorLayout mRootCL;
    @Bind(R.id.rv_content)
    RecyclerView mContentRV;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initData();
    }

    private void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        // 设置布局管理器
        mContentRV.setLayoutManager(layoutManager);
        ArrayList dataList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            dataList.add("Timmy:" + i);
        }
        CustomAdapter adapter = new CustomAdapter(dataList);
        mContentRV.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void onClick(View v) {
        Snackbar snackbar = Snackbar.make(mRootCL,
                "我是普通 Snackbar", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
