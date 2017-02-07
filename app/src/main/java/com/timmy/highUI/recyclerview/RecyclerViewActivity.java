package com.timmy.highUI.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.recyclerview.adapter.SimpleAdapter;
import com.timmy.highUI.recyclerview.autoPoll.AutoPollRecyclerActivity;
import com.timmy.highUI.recyclerview.decoration.DividerGridItemDecoration;
import com.timmy.highUI.recyclerview.decoration.DividerItemDecoration;
import com.timmy.highUI.recyclerview.interactive.InteractiveRecyclerView;
import com.timmy.highUI.recyclerview.wrapRecyclerView.WarpRecyclerViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RecyclerView的使用:包括 动画方式添加条目,删除条目,
 * 打造通过的RecyclerAdapter
 * RecyclerView的分割线处理
 * RecyclerView的item滑动动画效果
 */
public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SimpleAdapter adapter;
    private RecyclerView.ItemDecoration mDecoration;
    int currLayout = 1;
    int layoutTag = 1;
//    private String str = "填充内容";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initToolBar();

//        ListView listView = null;
//        listView.addHeaderView(null);
//        listView.addFooterView(null);
//        ListAdapter listAdapter = null;
//        listView.setAdapter(listAdapter);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleView);

        Random random = new Random();
        List<String> lists = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            lists.add("Item " + i);// + "-" + str.substring(random.nextInt(str.length())));
        }

        //recyclerView的使用步骤:
        //1.设置布局控制器
        //2.设置分割线
        //3.setAdapter
        adapter = new SimpleAdapter(this, lists);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mDecoration = new DividerGridItemDecoration(this);
        mRecyclerView.addItemDecoration(mDecoration);

        mRecyclerView.setAdapter(adapter);

        adapter.setOnSimpleClickListener(new SimpleAdapter.OnSimpleClickListener() {
            @Override
            public void onSimpleClick(String data, int position) {
                Toast.makeText(RecyclerViewActivity.this, "position:" + position + " " + data, Toast.LENGTH_SHORT).show();
            }
        });
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
                return true;
            case R.id.menu_remove:
                removeItem();
                return true;
            case R.id.menu_layout_manager:
                changeLayoutManager();
                return true;
            case R.id.menu_interactive:
                Util.gotoNextActivity(this, InteractiveRecyclerView.class);
                return true;
            case R.id.menu_auto_poll:
                Util.gotoNextActivity(this, AutoPollRecyclerActivity.class);
                return true;
            case R.id.menu_recycler_warp:
                Util.gotoNextActivity(this, WarpRecyclerViewActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeLayoutManager() {
        mRecyclerView.removeItemDecoration(mDecoration);

        layoutTag++;
        currLayout = layoutTag % 3;
        switch (currLayout) {
            case 1:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
                break;
            case 2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                mDecoration = new DividerGridItemDecoration(this);
                break;
            case 0:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                mDecoration = new DividerGridItemDecoration(this);
                break;
        }
        mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.requestLayout();
//        adapter.notifyDataSetChanged();
    }

    private void removeItem() {
        adapter.removeItem(5);
    }

    private void addItem() {
        adapter.addItem(3);
    }
}
