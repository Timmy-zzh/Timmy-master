package com.timmy.highUI.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的使用:包括 动画方式添加条目,删除条目,
 * 打造通过的RecyclerAdapter
 * RecyclerView的分割线处理
 * RecyclerView的item滑动动画效果
 */
public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SimpleAdapter adapter;
    private boolean isGrid;
    private RecyclerView.ItemDecoration mDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initToolBar();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleView);

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 61; i++) {
            lists.add("Item " + i);
        }

        //recyclerView的使用步骤:
        //1.设置布局控制器
        //2.设置分割线
        //3.setAdapter
        adapter = new SimpleAdapter(this, lists);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mDecoration = new DividerGridItemDecoration(this);
        mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addItem();
                break;
            case R.id.menu_remove:
                removeItem();
                break;
            case R.id.menu_layout_manager:
                changeLayoutManager();
                break;
        }
        return true;
    }

    private void changeLayoutManager() {
        mRecyclerView.removeItemDecoration(mDecoration);
        if (!isGrid) {
            mDecoration = new DividerGridItemDecoration(this);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        mRecyclerView.addItemDecoration(mDecoration);
        isGrid = !isGrid;
    }

    private void removeItem() {
        adapter.removeItem(5);
    }

    private void addItem() {
        adapter.addItem(3);
    }
}
