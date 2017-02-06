package com.timmy.highUI.motionEvent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.library.util.Logger;
import com.timmy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MotionEventActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {

    private static final java.lang.String TAG = "timmy";
    private int[] intArr = new int[]{10, 54, 89, 15, 45};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);
        initToolBar();

//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
//        Button button = (Button) findViewById(R.id.button);
//
//        relativeLayout.setOnTouchListener(this);
//        button.setOnTouchListener(this);
//
//        relativeLayout.setOnClickListener(this);
//        button.setOnClickListener(this);
//
//    }

//}

        MyLinearLayout myLinearLayout = (MyLinearLayout) findViewById(R.id.my_linear_layout);
        MyButton myButton = (MyButton) findViewById(R.id.my_button);
        MyTextView myTextView = (MyTextView) findViewById(R.id.my_textView);

//        myLinearLayout.setOnTouchListener(this);
//        myButton.setOnTouchListener(this);
//
//        myLinearLayout.setOnClickListener(this);
//        myButton.setOnClickListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Logger.d(TAG, "MotionEventActivity--onTouch:" + v);
        return false;
    }

    @Override
    public void onClick(View v) {
        Logger.d(TAG, "MotionEventActivity--onClick:" + v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_motion_event,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_nest:
                Util.gotoNextActivity(this,ScrollListViewActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

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

//        Logger.d(TAG,"数据结构算法");
//        int   a =   4;
//        int[] intArr2 = intArr;
//        for (int i : intArr) {
//            Logger.d(TAG,"INT:"+i);
//        }
//
//        intArr2[2] = 99;
//        for (int i : intArr2) {
//            Logger.d(TAG,"INT22:"+i);
//        }
//
//        List<String> list = new ArrayList<>();
//    }
//}
