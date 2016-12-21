package com.timmy.highUI.recyclerview.interactive;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.R;
import com.timmy.highUI.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.timmy.highUI.recyclerview.adapter.BaseViewHolder;

import java.util.Collections;

/**
 * Created by admin on 2016/12/4.
 */

public class QQAdapter extends BaseRecyclerViewAdapter<String> implements ItemTouchListener {

    private final ItemDragListener mDragListener;

    public QQAdapter(Context context, ItemDragListener listener) {
        super(context);
        this.mDragListener = listener;
    }

    @Override
    protected int inflaterItemLayout(int viewType) {
        return R.layout.item_interactive;
    }

    @Override
    protected void bindData(final BaseViewHolder holder, int position, String str) {
//        holder.setImageResource(R.id.iv_logo, qqMessage.getLogo());
        holder.setText(R.id.tv_name, str);
//        holder.setText(R.id.tv_lastMsg, qqMessage.getLastMsg());
//        holder.setText(R.id.tv_time, qqMessage.getTime());

        //设置logo的拖拽监听
        holder.getView(R.id.iv_logo).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mDragListener != null) {
                        mDragListener.onItemDrag(holder);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onItemClickListener(View itemView, int position, String str) {

    }

    @Override
    public boolean onItemRemove(int position) {
        getRealDatas().remove(position);
        notifyItemRemoved(position);
        return true;
    }

    @Override
    public boolean onItemMoved(int desPos, int targetPos) {
        Collections.swap(getRealDatas(), desPos, targetPos);
        notifyItemMoved(desPos, targetPos);
        return true;
    }
}
