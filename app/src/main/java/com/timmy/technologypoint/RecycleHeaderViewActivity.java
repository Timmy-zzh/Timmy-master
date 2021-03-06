package com.timmy.technologypoint;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.timmy.R;
import com.timmy.home.MainContentAdapter;
import com.timmy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 为RecycleView添加头文件
 */
public class RecycleHeaderViewActivity extends BaseActivity {

    @BindView(R.id.rv_recycleView)
    RecyclerView mRecyclerView;
    private int mPage = 1;
    private RecyclerView.LayoutManager layoutManager;
    private MainContentAdapter adapter;
    private List<String> dataList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_header_view);
        ButterKnife.bind(this);
        initToolBar();
        initRecycleView();
        initData();
    }

    private void initRecycleView() {
        switch (mPage) {
            case 1:
                layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                break;
            case 2:
                layoutManager = new GridLayoutManager(this, 2);
                break;
            case 3:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MainContentAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        String str = "";
        for (int i = 0; i < 15; i++) {
            str = "内容信息" + i;
            dataList.add(str);
        }
//        adapter.setData(dataList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
