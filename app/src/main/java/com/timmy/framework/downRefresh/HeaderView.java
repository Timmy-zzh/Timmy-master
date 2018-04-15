package com.timmy.framework.downRefresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.timmy.R;

/**
 * 下拉刷新设置的头部,设置mxl布局
 */
public class HeaderView extends FrameLayout {

    private TextView textView;
    private ProgressBar progressBar;

    public HeaderView(@NonNull Context context) {
        this(context,null);
    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_refresh_header, this);

        textView = findViewById(R.id.tv_state);
        progressBar = findViewById(R.id.pb_loading);

    }

    public void setTextState(String text){
        textView.setText(text);
    }

}
