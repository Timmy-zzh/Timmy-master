package com.timmy.framework.imageLoaderFw;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.TimmyImageLoader;
import com.timmy.highUI.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.timmy.highUI.recyclerview.adapter.BaseViewHolder;

/**
 * Created by admin on 2017/6/16.
 */
public class ImageAdapter extends BaseRecyclerViewAdapter<String> {

    public ImageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int inflaterItemLayout(int viewType) {
        return R.layout.item_image;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, String s) {
        ImageView imageView = (ImageView) holder.getView(R.id.iv_image);
        TimmyImageLoader.getInstance().displayImage(imageView,s);
    }

    @Override
    protected void onItemClickListener(View itemView, int position, String s) {

    }
}
