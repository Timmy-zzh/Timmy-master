package com.timmy.other;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.timmy.R;
import com.timmy.advance.animation.AnimationActivity;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.dialog.DialogActivity;
import com.timmy.technologypoint.AutoPlayPicturesActivity;
import com.timmy.technologypoint.CameraPictureActivity;
import com.timmy.technologypoint.ClipToOutlineActivity;
import com.timmy.technologypoint.GlideImageActivity;
import com.timmy.technologypoint.Picture9Activity;
import com.timmy.technologypoint.RecycleHeaderViewActivity;
import com.timmy.technologypoint.TimmyHealthActivity;
import com.timmy.technologypoint.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/23.
 * 测试提交
 */
public class TechnologyPointActivity extends BaseActivity {

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.btn_auto_images)
    Button btn_autoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_point);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        initToolBar();
    }

    @OnClick({R.id.btn_auto_images, R.id.btn_camera_picture,
            R.id.btn_animation, R.id.btn_glide, R.id.btn_clip,
            R.id.btn_dialog, R.id.btn_view, R.id.btn_recycleview,
            R.id.btn_toolbar,R.id.btn_coordi,R.id.btn_9pic})
    public void technology(View v) {
        switch (v.getId()) {
            case R.id.btn_auto_images:
                openActivity(AutoPlayPicturesActivity.class);
                break;
            case R.id.btn_camera_picture:
                openActivity(CameraPictureActivity.class);
                break;
            case R.id.btn_animation:
                openActivity(AnimationActivity.class);
                break;
            case R.id.btn_glide:
                openActivity(GlideImageActivity.class);
                break;
            case R.id.btn_clip:
                openActivity(ClipToOutlineActivity.class);
                break;
            case R.id.btn_dialog:
                openActivity(DialogActivity.class);
                break;
            case R.id.btn_view:
                openActivity(TimmyHealthActivity.class);
                break;
            case R.id.btn_recycleview:
                openActivity(RecycleHeaderViewActivity.class);
                break;
            case R.id.btn_toolbar:
                openActivity(ToolBarActivity.class);
                break;
            case R.id.btn_coordi:

                break;
            case R.id.btn_9pic:
                openActivity(Picture9Activity.class);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
