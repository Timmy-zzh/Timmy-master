package com.timmy.highUI.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 * Adapter基类
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private List<T> realDatas;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mCLickLongListener;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
        realDatas = new ArrayList<>();
    }

    public List<T> getRealDatas() {
        return realDatas;
    }

    public void setRealDatas(List<T> realDatas) {
        this.realDatas = realDatas;
    }

    //设置数据
    public BaseRecyclerViewAdapter setData(List<T> realDatas) {
        this.realDatas = realDatas;
        notifyDataSetChanged();
        return this;
    }

    /**
     * 添加更多数据
     */
    public BaseRecyclerViewAdapter addMoreData(List<T> realDatas) {
        this.realDatas.addAll(realDatas);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 绑定布局界面
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(inflaterItemLayout(viewType), parent, false);
        return new BaseViewHolder(itemView);
    }

    /**
     * 往控件中填充数据
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        bindData(holder, position, realDatas.get(position));
        //在这里设置Item的点击事件
        if (mClickListener == null) {
            mClickListener = new OnItemClickListener<T>() {
                @Override
                public void onItemClick(View itemView, int position, T t) {
                    //让子类去实现
                    onItemClickListener(itemView,position,t);
                }
            };
        }
        holder.itemView.setOnClickListener(new TimmyItemClickListener(position));
        if (mCLickLongListener == null) {
            mCLickLongListener = new OnItemLongClickListener<T>() {
                @Override
                public void onItemLongClick(View itemView, int position, T t) {
                    onItemLongClickListener(itemView,position,t);
                }
            };
        }
        holder.itemView.setOnLongClickListener(new TimmyItemLongClickListener(position));

    }


    //获取Item数量
    @Override
    public int getItemCount() {
        return realDatas.size();
    }


    private class TimmyItemClickListener implements View.OnClickListener {
        private  int mPosition;

        public TimmyItemClickListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null&& view != null) {
                mClickListener.onItemClick(view, mPosition, realDatas.get(mPosition));
            }
        }
    }


    private class TimmyItemLongClickListener implements View.OnLongClickListener {
        private  int mPosition;

        public TimmyItemLongClickListener(int position) {
            this.mPosition = position;
        }

        @Override
        public boolean onLongClick(View view) {
            if (mCLickLongListener != null&& view != null) {
                mCLickLongListener.onItemLongClick(view, mPosition, realDatas.get(mPosition));
                return true;
            }
            return false;
        }
    }

    /**
     * Item的点击事件接口
     */
    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, int position, T t);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View itemView, int position, T t);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mCLickLongListener = listener;
    }

    /**
     * 交给子类自己去填充Item布局
     */
    protected abstract int inflaterItemLayout(int viewType);

    protected abstract void bindData(BaseViewHolder holder, int position, T t);

    protected abstract void onItemClickListener(View itemView, int position, T t);

    private void onItemLongClickListener(View itemView, int position, T t) {

    }
}
