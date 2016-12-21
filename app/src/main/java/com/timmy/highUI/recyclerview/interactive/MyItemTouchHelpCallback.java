package com.timmy.highUI.recyclerview.interactive;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2016/12/5.
 */
public class MyItemTouchHelpCallback extends ItemTouchHelper.Callback {

    private final String TAG = this.getClass().getSimpleName();
    private final ItemTouchListener mItemTouchListener;

    public MyItemTouchHelpCallback(ItemTouchListener listener) {
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
     * 是否支持item左右滑动功能
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 是否支持item长按拖拽功能
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * item拖拽回调的方法
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Logger.d(TAG, "onMove");
        return mItemTouchListener.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    /**
     * recyclerview滑动时，会调用该方法－》设置接口，让接口的实现类去真是处理
     *
     * @param viewHolder
     * @param direction  滑动方向，可以判断是向左滑动还是向右
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Logger.d(TAG, "onSwiped");
        mItemTouchListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    /**
     * item左右滑动时,改变item的样式
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Logger.d("dx:" + dX + " -dy:" + dY);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float scale = 1 -  (Math.abs(dX) / viewHolder.itemView.getWidth());// 1-0
            viewHolder.itemView.setScaleX(scale);
            viewHolder.itemView.setScaleY(scale);
            viewHolder.itemView.setAlpha(scale);
        }
        //从右向左滑动,超过删除宽度的一半,就让删除界面显示
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX < 0) {
//            LinearLayout container = (LinearLayout) viewHolder.itemView.findViewById(R.id.ll_container);
//            RelativeLayout leftCon = (RelativeLayout) viewHolder.itemView.findViewById(R.id.rl_left_container);
//            TextView delete = (TextView) viewHolder.itemView.findViewById(R.id.tv_delete);
//            if (Math.abs(dX) < delete.getWidth() / 2) {
//                //向左滑动未超过删除控件宽度的一半->还原
////                viewHolder.itemView.
//            } else {
//                LinearLayout.LayoutParams conLp = (LinearLayout.LayoutParams) container.getLayoutParams();
//                LinearLayout.LayoutParams leftLp = (LinearLayout.LayoutParams) leftCon.getLayoutParams();
//                delete.setWidth(leftLp.width / 5);
//                conLp.setMargins(leftLp.width / 5, 0, 0, 0);
//            }
//        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }

    /**
     * 当item拖拽或滑动时调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        Logger.d("actionState:" + actionState);
        //当item拽或滑动时改变item的背景-->放开时,item背景恢复
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.GRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        //处理item布局复用问题
        viewHolder.itemView.setScaleX(1.0f);
        viewHolder.itemView.setScaleY(1.0f);
        viewHolder.itemView.setAlpha(1.0f);
        super.clearView(recyclerView, viewHolder);
    }
}
