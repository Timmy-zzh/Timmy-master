package com.timmy.highUI.recyclerview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * ViewHolder只做View的缓存,不关心数据内容
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    //创建View容器,根据key为控件id
    private SparseArray<View> viewArray;

    public BaseViewHolder(View itemView) {
        super(itemView);
        viewArray = new SparseArray<>();
    }

    /**
     * 获取布局中的View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(@IdRes int viewId) {
        View view = viewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewArray.put(viewId, view);
        }
        return (T) view;
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public BaseViewHolder setText(int viewId, String s) {
        TextView textView = findViewById(viewId);
        textView.setText(s);
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int visibility) {
        View view = findViewById(viewId);
        view.setVisibility(visibility);
        return this;
    }
}
