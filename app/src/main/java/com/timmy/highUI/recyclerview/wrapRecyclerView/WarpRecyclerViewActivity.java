package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.recyclerview.adapter.SimpleAdapter;
import com.timmy.home.MainContentAdapter;
import com.timmy.home.model.MainModel;

import java.util.ArrayList;
import java.util.List;

public class WarpRecyclerViewActivity extends BaseActivity {

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warp_recycler_view);
        initToolBar();

        WarpRecyclerView recyclerView = (WarpRecyclerView) findViewById(R.id.rv_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        TextView headerView = new TextView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(params);
        headerView.setText("我是HeaderView");
        recyclerView.addHeaderView(headerView);

        TextView footerView = new TextView(this);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footerView.setLayoutParams(params);
        footerView.setText("我是FooterView");
        recyclerView.addFooterView(footerView);

        for (int i = 0; i < 10; i++) {
            list.add("item:" + i);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list);

        recyclerView.setAdapter(adapter);
    }
}
