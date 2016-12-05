package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2016/12/5.
 */
public class MyItemTouchHelpCallback extends ItemTouchHelper.Callback {

    private final java.lang.String TAG = this.getClass().getSimpleName();

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Logger.d(TAG, "getMovementFlags");
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Logger.d(TAG, "onMove");
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Logger.d(TAG, "onSwiped");
    }
}
