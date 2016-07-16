package com.timmy.rxjava.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timmy.rxjava.R;
import com.timmy.rxjava.model.AppInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppHolder> {

    private Context mContext;
    private List<AppInfo> mData;

    public AppInfoAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<AppInfo> listData) {
        this.mData = listData;
        notifyDataSetChanged();
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app_info, parent, false));
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        AppInfo appInfo = mData.get(position);
        holder.mAppIcon.setImageDrawable(appInfo.getAppIcon());
//        ImageUtil.load(holder.mAppIcon, appInfo.getIcon());
        holder.mAppName.setText(appInfo.getAppName());
        holder.mAppInfo.setText(appInfo.getPackageName() + "-" + appInfo.getVersionName());
        holder.mIsSystem.setText(appInfo.isSystem() ? "系统应用" : "普通应用");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class AppHolder extends RecyclerView.ViewHolder {
        private ImageView mAppIcon;
        private TextView mAppName;
        private TextView mAppInfo;
        private TextView mIsSystem;

        public AppHolder(View itemView) {
            super(itemView);
            mAppIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mAppName = (TextView) itemView.findViewById(R.id.tv_name);
            mAppInfo = (TextView) itemView.findViewById(R.id.tv_app_info);
            mIsSystem = (TextView) itemView.findViewById(R.id.tv_system);
        }
    }
}
