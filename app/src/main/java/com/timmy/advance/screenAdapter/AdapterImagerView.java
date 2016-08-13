package com.timmy.advance.screenAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 适配屏幕的自定义ImageView,只要知道图片的宽高比,通过计算处理,展示的图片效果不出现拉伸变形.
 */
public class AdapterImagerView extends ImageView {
    public AdapterImagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
