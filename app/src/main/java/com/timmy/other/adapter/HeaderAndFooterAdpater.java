package com.timmy.other.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class HeaderAndFooterAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 10000;
    private static final int TYPE_FOOTER = 20000;
    private RecyclerView.Adapter mInnerAdapter;
    //存放头文件集合
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();


    private List<String> dataList;
    private Context context;

    public HeaderAndFooterAdpater(Context context) {
        this.context = context;
    }

    public HeaderAndFooterAdpater(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
    }

    public void setData(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position < getFootersCount();
    }

    //添加头部
    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + TYPE_HEADER, view);
    }

    public void addFooterView(View view) {
        mFooterViews.put(mFooterViews.size() + TYPE_FOOTER, view);
    }

    private int getHeadersCount() {
        return mHeaderViews.size();
    }

    private int getFootersCount() {
        return mFooterViews.size();
    }

    //adapter真实个数
    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    //获取类型
    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)){
            return mHeaderViews.keyAt(position);
        }else if(isFooterViewPos(position)){
            return mFooterViews.keyAt(position-getHeadersCount()-getRealItemCount());
        }
        return super.getItemViewType(position);
    }

    //绑定布局文件--根据不同的类型进行控件绑定
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (mHeaderViews.get(viewType)!=null){
//            RecyclerView.ViewHolder holder = RecyclerView.ViewHolder.on
//        }
//
//
//
//        View view = View.inflate(context, R.layout.item_tab_pager, null);
//        return new RecyclerView.ViewHolder(view);
        return null;
    }

    //为控件展示数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        holder.mContent.setText(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return getHeadersCount()+getRealItemCount()+getFootersCount();
    }

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        @Bind(R.id.tv_content)
//        TextView mContent;
//        @Bind(R.id.tv_type)
//        TextView mType;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
}
