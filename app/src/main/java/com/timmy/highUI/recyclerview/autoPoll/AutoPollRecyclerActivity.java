package com.timmy.highUI.recyclerview.autoPoll;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.recyclerview.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AutoPollRecyclerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_poll);
        initToolBar();
        initView();
    }

    private void initView() {
        AutoPollRecyclerView mRecyclerView = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; ) {
            list.add(" Item: " + ++i);
        }
        AutoPollAdapter adapter = new AutoPollAdapter(this, list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
        mRecyclerView.setAdapter(adapter);
        if (true) //保证
            mRecyclerView.start();
    }
}
