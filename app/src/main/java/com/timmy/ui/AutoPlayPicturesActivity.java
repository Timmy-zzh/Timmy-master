package com.timmy.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.timmy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/23.
 * 自动轮播图
 * 实现功能:根据手势滑动的ViewPager图片,和点的容器随图片的滑动而改变
 * 设置ViewPager图片数据(本地数据-网络获取),设置点的数据
 */
public class AutoPlayPicturesActivity extends BaseActivity {

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.vp_viewPage)
    ViewPager vp_viewPager;
    @Bind(R.id.ll_point_container)
    LinearLayout ll_pointContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_pictures);
        ButterKnife.bind(this);
    }


}
