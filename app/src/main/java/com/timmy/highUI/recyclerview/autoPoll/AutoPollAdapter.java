package com.timmy.highUI.recyclerview.autoPoll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.R;
import com.timmy.highUI.recyclerview.adapter.BaseViewHolder;
import com.timmy.highUI.recyclerview.adapter.SimpleAdapter;

import java.util.List;

/**
 * Created by admin on 2016/12/5.
 */

public class AutoPollAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final Context mContext;
    private final List<String> mData;

    public AutoPollAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        String data = mData.get(position%mData.size());
        holder.setText(R.id.tv_content,data);

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
