package com.timmy.advance.motionEvent;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.R;
import com.timmy.library.util.Logger;
import com.timmy.base.BaseActivity;

public class MotionEventActivity extends BaseActivity {

    private static final java.lang.String TAG = MotionEventActivity.class.getSimpleName();
    private int[] intArr = new int[]{10,54,89,15,45};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);
        initToolBar();
        MyButton myButton = (MyButton) findViewById(R.id.my_button);

        MyTextView myTextView = (MyTextView) findViewById( R. id.my_textView);

        myButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Logger.d(TAG, "--onTouch--ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Logger.d(TAG, "--onTouch--ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Logger.d(TAG, "--onTouch--ACTION_UP");
                        break;
                }
                return false;
//                return true;
            }
        });


        //位移运算
//        Logger.d(TAG, "位运算操作");
//        int a = 5, b = 3;
//        Logger.d(TAG, "5->a = " + Integer.toBinaryString(a) + "   3->b = " + Integer.toBinaryString(b));
//        Logger.d(TAG, "-5 = " + Integer.toBinaryString(-5));//为5的二进制的反码加一
//
//        Logger.d(TAG, "位或   a|b=" + (a | b));
//        Logger.d(TAG, "位与   a&b=" + (a & b));
//        Logger.d(TAG, "位异或 a^b=" + (a ^ b));
//        Logger.d(TAG, "左移   a<<2=" + (a << 2));//低位补0
//        Logger.d(TAG, "右移   a>>2=" + (a >> 2));//高位补0
//
//        Logger.d(TAG, "右移   5>>3=" + (5 >> 3));
//        Logger.d(TAG, "右移   -5>>3=" + (-5 >> 3));//高位补1
//        Logger.d(TAG, "无符号右移   -5>>>3=" + (-5 >>> 3));//高位补0
//        Logger.d(TAG, "位非   ~5=" + (~5));//第n为0时结果为1,反正为0

        Logger.d(TAG,"数据结构算法");
        int   a =   4;
        int[] intArr2 = intArr;
        for (int i : intArr) {
            Logger.d(TAG,"INT:"+i);
        }

        intArr2[2] = 99;
        for (int i : intArr2) {
            Logger.d(TAG,"INT22:"+i);
        }


    }
}
