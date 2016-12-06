package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.support.v7.widget.RecyclerView;


/**
 * Created by timmy1 on 16/12/6.
 */

public interface ItemTouchListener {

    /**
     * 左右滑动时，adapter回调该方法去删除该位置的item
     * @param position
     */
    boolean onItemDismiss(int position);

    /**
     * 上下拖拽时回调方法，adpater将两个位置的item调换位置
     * @param desPos
     * @param targetPos
     */
    boolean onItemMoved(int desPos,int targetPos);

}
