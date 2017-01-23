package com.timmy.highUI.animatoion;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class FrameAnimationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        initToolBar();

        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.frame_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

    }
}
