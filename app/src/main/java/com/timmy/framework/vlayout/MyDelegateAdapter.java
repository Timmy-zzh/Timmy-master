package com.timmy.framework.vlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.timmy.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2017/10/24.
 * 使用DelegateAdapter首先需要自定义一个他的内部类Adapter,传入LayoutHelper和需要的数据
 * 与普通Adapter多个一个方法onCreateLayoutHelper
 */
public class MyDelegateAdapter extends DelegateAdapter.Adapter<MyDelegateAdapter.MainViewHolder> {

    private VirtualLayoutManager.LayoutParams layoutParams;
    private Context context;
    private LayoutHelper layoutHelper;
    private int count;
    private ArrayList<HashMap<String, Object>> listItem;

    public MyDelegateAdapter(Context context, LayoutHelper layoutHelper, int count, ArrayList<HashMap<String, Object>> listItem) {
        this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(VirtualLayoutManager.LayoutParams.MATCH_PARENT, 300), listItem);

    }
    public MyDelegateAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams, ArrayList<HashMap<String, Object>> listItem) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.listItem = listItem;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    //创建布局
    @Override
    public MyDelegateAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_image,parent,false));
    }

    //绑定数据
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(layoutParams));
        holder.textView.setText((String) listItem.get(position).get("ItemTitle"));
    }

    //返回item个数
    @Override
    public int getItemCount() {
        return count;
    }

     static class MainViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView;
         public final TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
            textView = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}
