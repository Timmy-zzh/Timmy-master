package com.timmy.advance.customViewGroup;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.library.util.Logger;
import com.timmy.ui.base.BaseActivity;

/**
 * 应用手势锁
 * 1.自定义属性-构造函数中获取
 * 2.onMeasure方法测量锁控件大小,-添加单个锁按钮-并且确定锁按钮的大小和放置位置
 * 3.onToucheEvent方法,根据手势不同,通知锁按钮改变状态,病绘制
 */
public class GestureLockViewActivity extends BaseActivity {

    private static final java.lang.String TAG = GestureLockViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock_view);
        initToolBar();

        final GestureLockViewGroup gestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gesture_lock_view);
        gestureLockViewGroup.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onLockSelected(int number) {
                Logger.d(TAG, "--selected number:" + number);
            }

            @Override
            public void isAnswerMatched(boolean isMatched) {
                Toast.makeText(GestureLockViewActivity.this, "isMatched:" + isMatched, android.widget.Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnMatchedExceedBoundary() {
                Toast.makeText(GestureLockViewActivity.this, "超过尝试次数:", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }
}
