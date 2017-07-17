package com.timmy.framework.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.R;

/**
 * mvp设计模式代码实现：
 * 思路：
 * activity作为v层
 * 数据加载和处理作为m层
 * 中间通信枢纽p层
 *
 * p持有v和m层的实际应用；每个activity都对应一个mvp组合，
 * 1.首先将v层需要执行的函数逻辑全部抽取出来制作成接口
 * 2.p层作为通信层主要作用就是就是让v层和m层的实例应用进行逻辑处理
 *
 */
public class MVPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
    }
}
