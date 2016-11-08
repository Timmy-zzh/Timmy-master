package com.timmy.advance.win8metro;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.library.util.Logger;
import com.timmy.library.util.Toast;
import com.timmy.base.BaseActivity;


/**
 * 高仿win效果的界面展示
 * 使用ImageView点击时,图片有缩放效果(先变小后还原到原始大小)
 * 先进行梯度测试--
 */
public class FollowWin8Activity extends BaseActivity {

    private final java.lang.String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_win8);
        initToolBar();
//        test();

        Win8ImageView mFour = (Win8ImageView) findViewById(R.id.wiv_four);
        mFour.setOnViewClickListener(new Win8ImageView.OnViewClickListener() {
            @Override
            public void onViewClick(Win8ImageView view) {
                Toast.toastShort("点击");
            }
        });
    }

    /**
     * 想让一个数先变大,后变小
     */
    private void test() {
        float val = 1;
        float s = 0.85f;
        int i = 0;
        s = (float) Math.sqrt(1 / s);//平方
        Logger.d(TAG, s + "");
        while (i < 5) {
            val = val * s;
            //val变大
            Logger.d(TAG, val + "");
            i++;
        }

        s = 0.85f;
        i = 0;
        s = (float) Math.sqrt(s);
        while (i < 5) {
            val = val * s;
            //val变小
            Logger.d(TAG, val + "");
            i++;
        }

    }
}
