package com.timmy.highUI.animatoion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class TranstionAnimationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_transtion_animation);
        initToolBar();
    }

    public void oldTransAnimation(View view) {
        startActivity(new Intent(this, TranAnimActivityB.class));
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    //需要5.0以上配置
    public void sceneTrans(View view) {
        startActivity(new Intent(this, TranAnimActivityB.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //共享元素开启动画
    public void sharedElement(View view) {
        startActivity(new Intent(this,TranAnimActivityB.class),ActivityOptions.makeSceneTransitionAnimation(this,view,"myView").toBundle());
    }

}
