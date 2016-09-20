package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/9/20.
 *
 *
 */
public class AdapterScrollView extends ScrollView {

    private ScrollAdapter mAdapter;

    public AdapterScrollView(Context context) {
        this(context,null);
    }

    public AdapterScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(ScrollAdapter adapter) {
        this.mAdapter = adapter;
    }
}
