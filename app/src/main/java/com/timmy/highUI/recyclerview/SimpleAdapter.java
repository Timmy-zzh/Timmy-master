package com.timmy.highUI.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;

import java.util.List;

/**
 * Created by admin on 2016/11/29.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private final Context mContext;
    private final List<String> mData;

    public SimpleAdapter(Context context, List<String> lists) {
        this.mContext = context;
        this.mData = lists;
    }

    public void addItem(int i) {
        mData.add(i,"abcd");
        notifyItemInserted(i);
    }

    public void removeItem(int i) {
        mData.remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_pager, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        String data = mData.get(position);
        holder.tv.setText(data);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
