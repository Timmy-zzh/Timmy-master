package com.timmy.highUI.motionEvent.nest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.timmy.library.util.Logger;

/**
 * onMeasure()方法中设置ListView的高度，处理ScrollView嵌套ListView时高度不展示的问题
 */
public class ExpandedListView extends ListView {

    private String TAG = this.getClass().getSimpleName();

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandedHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandedHeight);
    }
}
