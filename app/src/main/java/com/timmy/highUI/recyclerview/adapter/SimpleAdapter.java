package com.timmy.highUI.recyclerview.adapter;

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
public class SimpleAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final Context mContext;
    private final List<String> mData;
    private OnSimpleClickListener mListener;

    public SimpleAdapter(Context context, List<String> lists) {
        this.mContext = context;
        this.mData = lists;
    }

    public void addItem(int i) {
        mData.add(i, "abcd");
        notifyItemInserted(i);
    }

    public void removeItem(int i) {
        mData.remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_pager, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        String data = mData.get(position);
        TextView textView = (TextView) holder.getView(R.id.tv_content);
        textView.setText(data);

        holder.itemView.setOnClickListener(new OnSimpleTestClick(position));
    }

//    class SimpleViewHolder extends RecyclerView.ViewHolder {
//
//        private final TextView tv;
//
//        public SimpleViewHolder(View itemView) {
//            super(itemView);
//            tv = (TextView) itemView.findViewById(R.id.tv_content);
//        }
//    }

    public interface OnSimpleClickListener {
        void onSimpleClick(String data, int position);
    }

    public void setOnSimpleClickListener(OnSimpleClickListener listener) {
        this.mListener = listener;
    }

    private class OnSimpleTestClick implements View.OnClickListener {

        private final int position;

        public OnSimpleTestClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.onSimpleClick(mData.get(position), position);
        }
    }
}
