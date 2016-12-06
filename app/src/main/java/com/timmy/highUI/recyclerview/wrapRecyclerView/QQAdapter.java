package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.content.Context;
import android.view.View;

import com.timmy.R;
import com.timmy.highUI.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.timmy.highUI.recyclerview.adapter.BaseViewHolder;

import java.util.Collections;

/**
 * Created by admin on 2016/12/4.
 */

public class QQAdapter extends BaseRecyclerViewAdapter<QQMessage> implements ItemTouchListener {

    public QQAdapter(Context context) {
        super(context);
    }

    @Override
    protected int inflaterItemLayout(int viewType) {
        return R.layout.item_qq;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, QQMessage qqMessage) {
        holder.setImageResource(R.id.iv_logo, qqMessage.getLogo());
        holder.setText(R.id.tv_name, qqMessage.getName());
        holder.setText(R.id.tv_lastMsg, qqMessage.getLastMsg());
        holder.setText(R.id.tv_time, qqMessage.getTime());
    }

    @Override
    protected void onItemClickListener(View itemView, int position, QQMessage qqMessage) {

    }

    @Override
    public boolean onItemDismiss(int position) {
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
