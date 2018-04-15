package com.timmy.framework.vlayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by admin on 2017/10/25.
 */

public class MyVirtualLayoutAdapter extends VirtualLayoutAdapter {

    public MyVirtualLayoutAdapter(@NonNull VirtualLayoutManager layoutManager) {
        super(layoutManager);
        HashMap hashMap = new HashMap();
        Hashtable hashtable = new Hashtable();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
