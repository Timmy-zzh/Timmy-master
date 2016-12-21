package com.timmy.highUI.recyclerview.interactive;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timmy.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2016/12/4.
 */
public class InteractiveAdapter extends RecyclerView.Adapter<InteractiveAdapter.InteractiveViewHolder> implements ItemTouchListener {

    private final ItemDragListener mDragListener;
    private final Context mContext;
    private List<String> mData;

    public InteractiveAdapter(Context context, ItemDragListener listener) {
        this.mContext = context;
        this.mDragListener = listener;
        mData = new ArrayList<>();
    }

    public void setData(List<String> datas) {
        this.mData = datas;
    }


    private List<String> getRealDatas() {
        return mData;
    }

    @Override
    public InteractiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_interactive, parent, false);
        return new InteractiveViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InteractiveViewHolder holder, int position) {
        String data = mData.get(position);
            holder.name.setText(data);
        holder.logo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mDragListener != null) {
                        mDragListener.onItemDrag(holder);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class InteractiveViewHolder extends RecyclerView.ViewHolder {

        private final ImageView logo;
        private final TextView name;

        public InteractiveViewHolder(View itemView) {
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    @Override
    public boolean onItemRemove(int position) {
        getRealDatas().remove(position);
        notifyItemRemoved(position);
        return true;
    }

    @Override
    public boolean onItemMoved(int desPos, int targetPos) {
        Collections.swap(getRealDatas(), desPos, targetPos);
        notifyItemMoved(desPos, targetPos);
        return true;
    }
}
