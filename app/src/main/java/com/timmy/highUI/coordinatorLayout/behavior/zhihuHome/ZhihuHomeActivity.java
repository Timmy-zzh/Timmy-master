package com.timmy.highUI.coordinatorLayout.behavior.zhihuHome;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.recyclerview.adapter.SimpleAdapter;
import com.timmy.highUI.recyclerview.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 高仿知乎首页界面：
 * 1.界面上下滑动时顶部Toolbar隐藏--使用AppBarLayout包括Toolbar设置scrollFlags
 * 2.右下角的Fab通过自定义Behavior控制显示和隐藏
 * 3.底部的LinearLayout通过自定义Behavior控制显示和隐藏
 */
public class ZhihuHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_home);
        initToolBar();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("Item:" + i);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list);
        recyclerView.setAdapter(adapter);

    }
}
