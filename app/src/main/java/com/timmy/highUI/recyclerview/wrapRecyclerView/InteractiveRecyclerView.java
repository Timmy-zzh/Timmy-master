package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * RecyclerView的交互动画
 * ItemTouchHelper类的使用
 */
public class InteractiveRecyclerView extends BaseActivity {

    @Bind(R.id.rv_recycleView)
    RecyclerView mRecyclerView;
    private QQAdapter adapter;
    private ItemTouchHelper touchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_recycler);
        ButterKnife.bind(this);
        initToolBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new QQAdapter(this);
        adapter.setData(DataUtils.init());
        mRecyclerView.setAdapter(adapter);

        //条目触摸辅助类
        ItemTouchHelper.Callback callBack = new MyItemTouchHelpCallback(adapter);
        touchHelper = new ItemTouchHelper(callBack);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
