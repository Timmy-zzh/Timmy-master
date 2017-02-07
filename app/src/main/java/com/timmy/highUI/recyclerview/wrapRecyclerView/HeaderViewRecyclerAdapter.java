package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/7.
 */

public class HeaderViewRecyclerAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 01110;
    private static final int TYPE_FOOT = 01111;
    private final RecyclerView.Adapter mAdapter;
    private final List<View> mHeaderViewInfos;
    private final List<View> mFooterViewInfos;

    public HeaderViewRecyclerAdapter(List<View> headerViewInfos,
                                     List<View> footerViewInfos,
                                     RecyclerView.Adapter adapter) {
        mAdapter = adapter;

        if (headerViewInfos == null) {
            mHeaderViewInfos = new ArrayList<>();
        } else {
            mHeaderViewInfos = headerViewInfos;
        }

        if (footerViewInfos == null) {
            mFooterViewInfos = new ArrayList<>();
        } else {
            mFooterViewInfos = footerViewInfos;
        }
    }

    //绑定xml布局文件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据viewType确定返回的viewHolder
        if (viewType == TYPE_HEADER) {
            //这样处理只能增加一个头部或尾部，当我需要增加多个头部和尾部时-》处理
            //当LayoutManager为GridLayout时,有怎么处理
            return new HeaderViewHodler(mHeaderViewInfos.get(0));
        } else if (viewType == TYPE_FOOT) {
            return new HeaderViewHodler(mFooterViewInfos.get(0));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    //xml文件绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头部
        if (position < getHeadersCount()) {
            return;
        }
        int adjPosition = position - getHeadersCount();
        if (mAdapter != null) {
            if (adjPosition < mAdapter.getItemCount()) {
                mAdapter.onBindViewHolder(holder, adjPosition);
                return;
            }
        }
        //footer
    }

    @Override
    public int getItemViewType(int position) {
        //头部类型
        int headersCount = getHeadersCount();
        if (position < headersCount) {
            return TYPE_HEADER;
        }

        //正真的条目部分
        int adjPosition = position - headersCount;
        if (mAdapter != null) {
            if (adjPosition < mAdapter.getItemCount()) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        //尾部
        return TYPE_FOOT;
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
    }

    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

    private class HeaderViewHodler extends RecyclerView.ViewHolder {
        public HeaderViewHodler(View view) {
            super(view);
        }
    }
}
