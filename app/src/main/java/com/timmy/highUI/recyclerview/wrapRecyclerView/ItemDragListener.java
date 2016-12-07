package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.support.v7.widget.RecyclerView;

/**
 * Created by admin on 2016/12/7.
 * 拖拽监听接口
 */
public interface ItemDragListener {

    void onItemDrag(RecyclerView.ViewHolder viewHolder);
}
