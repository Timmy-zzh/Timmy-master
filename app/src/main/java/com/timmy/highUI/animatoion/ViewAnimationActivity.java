package com.timmy.highUI.animatoion;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.base.BaseActivity;
import com.timmy.library.util.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * view 动画（补间动画）
 */
public class ViewAnimationActivity extends BaseActivity {

    @Bind(R.id.iv_image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        ButterKnife.bind(this);
        initToolBar();
//        ImageView
    }

    @OnClick({R.id.btn_alpha,
            R.id.btn_scale,
            R.id.btn_rotate,
            R.id.btn_translate,
            R.id.btn_set,
            R.id.btn_reset,
            R.id.iv_image})
    public void onAnimation(View view) {
        switch (view.getId()) {
            case R.id.btn_alpha:
                alphaAnimation();
//                alphaAnimationXml();
                break;
            case R.id.btn_scale:
                scaleAnimationXml();

                break;
            case R.id.btn_rotate:
                rotateAnimation();

                break;
            case R.id.btn_translate:

                translateAnimation();
                break;
            case R.id.btn_set:
                setAnimationXml();

                break;
            case R.id.btn_reset:
                image.clearAnimation();
                break;
            case R.id.iv_image:
                Toast.toastShort("点击");
                break;
            default:
                break;
        }
    }

    private void setAnimationXml() {
        Animation setAnimation = AnimationUtils.loadAnimation(this, R.anim.set_animation);
        image.startAnimation(setAnimation);
    }

    private void scaleAnimationXml() {
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        image.startAnimation(scaleAnimation);

    }

    //通过xml文件设置动画－》开启动画
    private void alphaAnimationXml() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.aplha_animation);
        image.startAnimation(animation);
    }

    private void alphaAnimation() {
        //透明度动画， 参数为从开始透明度到结束透明度，值为 0f～1.0f， 0为全透明，1为不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.1f);
        //设置动画时间
        alphaAnimation.setDuration(1000);
        //动画播放后保持当前状态
        alphaAnimation.setFillAfter(true);
        //动画重复执行
        alphaAnimation.setRepeatCount(2);
        //动画重置执行的方式：重新开始执行／反转执行
//        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        //开始动画
        image.startAnimation(alphaAnimation);
    }

    private void rotateAnimation() {
        //以自己为参照物，设置的坐标进行旋转动画
//        RotateAnimation rotateAnimation = new RotateAnimation(0f, 135f, 60f, 60f);

        RotateAnimation rotateAnimation = new RotateAnimation(0f, 235f,//旋转角度，正数为顺时针方向，负数为逆时针方向
                Animation.RELATIVE_TO_SELF, 0.5f,//冒点，相对于自己位置的x方向50%距离
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        //为动画设置插入器
        rotateAnimation.setInterpolator(new AccelerateInterpolator());
        image.startAnimation(rotateAnimation);
    }

    private void translateAnimation() {
        //偏移动画执行的位置，默认是以自己为参照物
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 800);
        translateAnimation.setDuration(1000);
        //设置动画插入器
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimation.setFillAfter(true);
        image.startAnimation(translateAnimation);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.frame_animation:
                Util.gotoNextActivity(this, FrameAnimationActivity.class);
                return true;
            case R.id.property_animation:
                Util.gotoNextActivity(this, PropertyAnimationActivity.class);
                return true;
            case R.id.transtion_animation:
                Util.gotoNextActivity(this, TranstionAnimationActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
