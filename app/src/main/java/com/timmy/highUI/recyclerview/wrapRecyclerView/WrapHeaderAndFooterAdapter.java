package com.timmy.highUI.recyclerview.wrapRecyclerView;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.highUI.recyclerview.adapter.BaseViewHolder;

/**
 * Created by admin on 2017/2/7.
 * 包含多个头部和尾部，
 * addHeaderView();
 * addFooterView();
 * <p>
 * 复写方法，
 * getItemCount()--item个数
 * getItemViewType()--item类型
 * onCreateViewHolder()--绑定布局
 * onBindViewHolder()--数据绑定
 * <p>
 * 使用SparseArrayCompat来保存头部和尾部view,等会要用到,--相当于Map类型,key值为integer
 * <p>
 * 处理不同LayoutManager下的界面展示
 */
public class WrapHeaderAndFooterAdapter extends RecyclerView.Adapter {

    private static final int BASE_ITEM_TYPE_HEADER = 10000;
    private static final int BASE_ITEM_TYPE_FOOTER = 20000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private RecyclerView.Adapter mAdapter;//真正的条目

    public WrapHeaderAndFooterAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public void addHeaderView(View view) {
        //保存的key值也就是后面要用到的viewType
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFooterView(View view) {
        //保存的key值也就是后面要用到的viewType
        mFooterViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFooterViews.size();
    }

    //当前位置的position是否在头部
    public boolean isHeaderViewPos(int position) {
        return position < getHeaderCount();
    }

    public boolean isFooterViewPos(int position) {
        return position >= getHeaderCount() + mAdapter.getItemCount();
    }

    //xml布局文件绑定
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据key值拿到对应的view
        if (mHeaderViews.get(viewType) != null) {
            return new BaseViewHolder(mHeaderViews.get(viewType));
        } else if (mFooterViews.get(viewType) != null) {
            return new BaseViewHolder(mFooterViews.get(viewType));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        mAdapter.onBindViewHolder(holder, position - getHeaderCount());
    }

    @Override
    public int getItemViewType(int position) {
        //头部
        if (isHeaderViewPos(position)) {
            //返回的是SparseArrayCompat的key值
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeaderCount() - getRealItemCount());
        }
        return mAdapter.getItemViewType(position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getFooterCount() + getRealItemCount();
    }

    public int getRealItemCount() {
        if (mAdapter != null) {
            return mAdapter.getItemCount();
        }
        return 0;
    }

//    private class HeaderViewHolder extends RecyclerView.ViewHolder {
//        public HeaderViewHolder(View view) {
//            super(view);
//        }
//    }

    //LayoutManager为GridManager时的处理
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        //头部
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    } else if (mFooterViews.get(viewType) != null) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    //处理LayoutManager为StaggeredGridLayoutManager
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        }
    }
}
