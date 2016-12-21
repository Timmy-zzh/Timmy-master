package com.timmy.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.advance.customView.XiuViewActivity;
import com.timmy.highUI.collapsingToolbarLayout.CollapsingToolbarLayoutActivity;
import com.timmy.highUI.recyclerview.RecyclerViewActivity;
import com.timmy.highUI.drawerLayout.DrawerLayoutActivity;
import com.timmy.highUI.snackBar.SnackBarActivity;
import com.timmy.highUI.stretchList.StretchListActivity;
import com.timmy.home.model.MainModel;
import com.timmy.home.model.MainTag;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainContentAdapter extends RecyclerView.Adapter<MainContentAdapter.TabHolder> {

    private List<MainModel> dataList;
    private Context context;

    public MainContentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MainModel> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public TabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_main_page, null);
        return new TabHolder(view);
    }

    @Override
    public void onBindViewHolder(TabHolder holder, final int position) {
        final MainModel model = dataList.get(position);
        holder.mContent.setText(model.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(context, dataList.get(position).getDesc(), android.widget.Toast.LENGTH_SHORT).show();
                switch (model.getTag()) {
                    case MainTag.TAG_XIUXIU:
                        Util.gotoNextActivity(context, XiuViewActivity.class);
                        break;
                    case MainTag.TAG_QQ_ZONE_STRETCH:
                        Util.gotoNextActivity(context, StretchListActivity.class);
                        break;
                    case MainTag.TAG_RECYCLER_VIEW:
                        Util.gotoNextActivity(context, RecyclerViewActivity.class);
                        break;
                    case MainTag.TAG_COLLAPSING_TOOLBAR_LAYOUT:
                        Util.gotoNextActivity(context, CollapsingToolbarLayoutActivity.class);
                        break;
                    case MainTag.TAG_SLIDESLIP:
                        Util.gotoNextActivity(context, DrawerLayoutActivity.class);
                        break;
                    case MainTag.TAG_SNACKBAR:
                        Util.gotoNextActivity(context, SnackBarActivity.class);
                        break;

                    default:
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class TabHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_desc)
        TextView mContent;

        public TabHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
