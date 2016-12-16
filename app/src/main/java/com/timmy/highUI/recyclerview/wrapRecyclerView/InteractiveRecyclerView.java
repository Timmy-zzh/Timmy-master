package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * RecyclerView的交互动画
 * ItemTouchHelper类的使用
 */
public class InteractiveRecyclerView extends BaseActivity implements ItemDragListener {

    @Bind(R.id.rv_recycleView)
    RecyclerView mRecyclerView;
    private InteractiveAdapter adapter;
    private ItemTouchHelper touchHelper;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_recycler);
        ButterKnife.bind(this);
        initToolBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        datas = new ArrayList<>();
        for (int i = 0; i < 21; ) {
            datas.add("Item" + ++i);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new InteractiveAdapter(this, this);
        adapter.setData(datas);
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

    /**
     * Adapter中logo设置了拖拽监听,让ItemTouchHelper去处理
     *
     * @param viewHolder
     */
    @Override
    public void onItemDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
}
