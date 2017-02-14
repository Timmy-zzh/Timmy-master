package com.timmy.highUI.animatoion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

import com.timmy.R;

public class TranAnimActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //分解
//        getWindow().setEnterTransition(new Explode().setDuration(2000));
//        getWindow().setExitTransition(new Explode().setDuration(2000));

        //滑动进入
//        getWindow().setEnterTransition(new Slide().setDuration(2000));
//        getWindow().setExitTransition(new Slide().setDuration(2000));

        //淡入淡出
//        getWindow().setEnterTransition(new Fade().setDuration(2000));
//        getWindow().setExitTransition(new Fade().setDuration(2000));

        setContentView(R.layout.activity_tran_anim);

    }
}
