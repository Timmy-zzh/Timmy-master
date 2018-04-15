package com.timmy.framework.downRefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timmy.R;
import com.timmy.base.SimpleAdapter;
import com.timmy.highUI.recyclerview.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        initView();
        initData();
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        swipeRefreshLayout.setRefreshing(true);
        //下拉刷新
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.removeItemDecoration(mDivider);
        mRecyclerView.addItemDecoration(mDivider);
        adapter = new SimpleAdapter(this);
        mRecyclerView.setAdapter(adapter);

//        adapter.setOnItemCli

    }

    private void initData() {
        List<String> pageListThree = new ArrayList<>();
        for (int i = 0; i < 39; i++) {
            pageListThree.add("Item : " + i);
        }
        adapter.setData(pageListThree);
    }
}

