package com.timmy.highUI.motionEvent.nest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.timmy.library.util.Logger;

/**
 * onMeasure()方法中设置ListView的高度，处理ScrollView嵌套ListView时高度不展示的问题
 */
public class ExpandedListView extends ListView {

    private String TAG = this.getClass().getSimpleName();
    private static int totalHeight;

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //方法一
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandedHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandedHeight);
    }

    /**
     * 方法二:根据每个item的高度重新设置listview的高度
     * 需要在代码中先设置Adapter后,手动调用
     * @param listView
     */
    public static void setListViewRealHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null || adapter.getCount() <= 0) {
            return;
        }

        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            View view = adapter.getView(i, null, listView);
            view.measure(0, 0);
            totalHeight = view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = totalHeight + listView.getDividerHeight() * (count - 1);
        listView.setLayoutParams(layoutParams);

    }

    //方法三:adapter.getItemViewType()--根据不同的itemViewType设置不同的xml布局


}
