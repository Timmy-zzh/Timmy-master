package com.timmy.advance.slidelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.timmy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public class ListAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private List<String> mDatas;

    public void setData(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    public ListAdapter(Context context) {
        super(context, R.layout.item_tab_pager);
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public String getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tab_pager, parent, false);

        TextView mContent = (TextView) convertView.findViewById(R.id.tv_content);
        mContent.setText(mDatas.get(position));

        return convertView;
    }
}
