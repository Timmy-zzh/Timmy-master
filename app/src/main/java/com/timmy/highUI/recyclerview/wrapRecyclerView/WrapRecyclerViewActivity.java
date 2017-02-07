package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.recyclerview.adapter.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装了头部和尾部的RecyclerView,处理方式有两种
 * 一.仿照ListView思路,继承RecyclerView添加addHeaderView()和addFooterView()方法,并重写setAdapter()方法,
 * 在内部新建一个新的Adapter类,根据头部和尾部进行处理
 * 二.直接继承ReyclerView.Adapter类,在内部处理,添加多个头部和尾部的处理和处理不同LayoutManager情况下的展示
 */
public class WrapRecyclerViewActivity extends BaseActivity {

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warp_recycler_view);
        initToolBar();

        WrapRecyclerView recyclerView = (WrapRecyclerView) findViewById(R.id.rv_recycleView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//
//        TextView headerView = new TextView(this);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        headerView.setLayoutParams(params);
//        headerView.setText("我是HeaderView");
//        recyclerView.addHeaderView(headerView);
//
//        TextView footerView = new TextView(this);
//        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        footerView.setLayoutParams(params);
//        footerView.setText("我是FooterView");
//        recyclerView.addFooterView(footerView);
//
//        for (int i = 0; i < 10; i++) {
//            list.add("item:" + i);
//        }
//        SimpleAdapter adapter = new SimpleAdapter(this, list);
//
//        recyclerView.setAdapter(adapter);

/////////////////////////////////////////////方式二

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        for (int i = 0; i < 10; i++) {
            list.add("item:" + i);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list);
        WrapHeaderAndFooterAdapter wrapAdapter = new WrapHeaderAndFooterAdapter(adapter);

        TextView headerView = new TextView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(params);
        headerView.setText("我是HeaderView1");
        wrapAdapter.addHeaderView(headerView);

        TextView headerView2 = new TextView(this);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView2.setLayoutParams(params);
        headerView2.setText("我是HeaderView2");
        wrapAdapter.addHeaderView(headerView2);

        TextView footerView = new TextView(this);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footerView.setLayoutParams(params);
        footerView.setText("我是FooterView");
        wrapAdapter.addFooterView(footerView);

        recyclerView.setAdapter(wrapAdapter);
    }
}
