package com.timmy.ui.technologypoint;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationActivity extends BaseActivity {

    private static final long TIME_DURATION = 1000;
    @Bind(R.id.iv_image)
    ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        initToolBar();

        initAnimationConfi();

    }

    @OnClick({R.id.btn_alpha, R.id.btn_scale, R.id.btn_rotate, R.id.btn_translate, R.id.btn_set})
    public void onAnimation(View view) {
        switch (view.getId()) {
            case R.id.btn_alpha:
                alphaAnimation();

                break;
            case R.id.btn_scale:

                break;
            case R.id.btn_rotate:

                break;
            case R.id.btn_translate:

                translateAnimation();
                break;
            case R.id.btn_set:

                break;
            default:
                break;
        }
    }

    private void translateAnimation() {

        TranslateAnimation translateAnimation = new TranslateAnimation(0,100,0,800);

        translateAnimation.setDuration(TIME_DURATION);
        //设置动画插入器
//        translateAnimation.setInterpolator(this,);
        translateAnimation.setFillAfter(true);
        iv_image.startAnimation(translateAnimation);

    }

    private void alphaAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,(float)0.1);
        //设置动画时间
        alphaAnimation.setDuration(TIME_DURATION);
        //动画播放后保持当前状态
        alphaAnimation.setFillAfter(true);
        iv_image.startAnimation(alphaAnimation);
    }

    private void initAnimationConfi() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
