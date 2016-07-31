package com.timmy.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 * 该Adapter适合加载纯List数据界面进行展示
 * 并设置加载更多效果
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private int TYPE_FOOTER = 0;//尾部
    private int TYPE_LIST = 1;//数据类型

    private Context context;
    private List<T> realDatas;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mCLickLongListener;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
        realDatas = new ArrayList<>();
    }

    //设置数据
    public BaseRecyclerViewAdapter setData(List<T> realDatas) {
        this.realDatas = realDatas;
        notifyDataSetChanged();
        return this;
    }

    /**
     * 添加更多数据
     *
     * @param realDatas
     * @return
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
//        if (viewType == TYPE_FOOTER) {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.view_footer_loading, parent, false);
//            return new FooterViewHolder(itemView);
//        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(inflaterItemLayout(viewType), parent, false);
        return new BaseViewHolder(itemView);
    }


    /**
     * 往控件中填充数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //底部加载更多动画
//        if (holder instanceof FooterViewHolder) {
//            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
//            footerViewHolder.animator.start();
//            return;
//        }
        bindData(holder, position, realDatas.get(position));
        //在这里设置Item的点击事件
        if (mClickListener == null) {
            mClickListener = new OnItemClickListener<T>() {
                @Override
                public void onItemClick(View itemView, int position, T t) {
                    //让子类去实现
                    onItemClickListener(itemView, position, t);
                }
            };
        }
        holder.itemView.setOnClickListener(getOnClickListener(position));
        if (mCLickLongListener == null) {
            mCLickLongListener = new OnItemLongClickListener<T>() {
                @Override
                public void onItemLongClick(View itemView, int position, T t) {
                    onItemLongClickListener(itemView, position, t);
                }
            };
        }
        holder.itemView.setOnLongClickListener(getOnLongClickListener(position));

    }


    /**
     * 获取Item的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return realDatas.get(position) != null ? TYPE_LIST : TYPE_FOOTER;
    }

    //获取Item数量
    @Override
    public int getItemCount() {
        return realDatas.size();
    }

    /**
     * RecyclerView的Adapter最后的底部类型
     */
//    public static class FooterViewHolder extends BaseViewHolder {
//
//        private ImageView mLoading;
//        private final AnimationDrawable animator;
//
//        public FooterViewHolder(View itemView) {
//            super(itemView);
//            mLoading = (ImageView) itemView.findViewById(R.id.iv_loading);
//            //拿到ImageView设置的背景动画
//            animator = (AnimationDrawable) mLoading.getDrawable();
//            animator.start();
//        }
//    }
    private View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null && view != null) {
                    mClickListener.onItemClick(view, position, realDatas.get(position));
                }
            }
        };
    }

    private View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mCLickLongListener != null && view != null) {
                    mCLickLongListener.onItemLongClick(view, position, realDatas.get(position));
                    return true;
                }
                return false;
            }
        };
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
     *
     * @param viewType
     * @return
     */
    protected abstract int inflaterItemLayout(int viewType);

    protected abstract void bindData(BaseViewHolder holder, int position, T t);

    protected abstract void onItemClickListener(View itemView, int position, T t);

    private void onItemLongClickListener(View itemView, int position, T t) {

    }
}
