package com.timmy.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainContentAdapter extends RecyclerView.Adapter<MainContentAdapter.TabHolder> {

    private List<String> dataList;
    private Context context;

    public MainContentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public TabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_tab_pager, null);
        return new TabHolder(view);
    }

    @Override
    public void onBindViewHolder(TabHolder holder, int position) {
        holder.mContent.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class TabHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_content)
        TextView mContent;
        @Bind(R.id.tv_type)
        TextView mType;

        public TabHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
