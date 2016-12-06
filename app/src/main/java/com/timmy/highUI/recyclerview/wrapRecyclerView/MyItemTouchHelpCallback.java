package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2016/12/5.
 */
public class MyItemTouchHelpCallback extends ItemTouchHelper.Callback {

    private final java.lang.String TAG = this.getClass().getSimpleName();
    private final ItemTouchListener mItemTouchListener;

    public MyItemTouchHelpCallback(ItemTouchListener listener){
        this.mItemTouchListener = listener;
    }

    /**
     * 该方法主要用于设置touch事件的方向：上下拖拽和左右滑动
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Logger.d(TAG, "getMovementFlags");
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//拖拽方向：上下
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//滑动方向：左右
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 是否支持item滑动功能
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 是否致辞item长按拖拽功能
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Logger.d(TAG, "onMove");
        return mItemTouchListener.onItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }

    /**
     * recyclerview滑动时，会调用该方法－》设置接口，让接口的实现类去真是处理
     * @param viewHolder
     * @param direction 滑动方向，可以判断是向左滑动还是向右
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Logger.d(TAG, "onSwiped");
        mItemTouchListener.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
