package com.timmy.advance.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.library.util.Logger;
import com.timmy.base.BaseActivity;

/**
 * 要实现ViewPager滑动时候的前后ItemView的滑动效果,
 * 首先要拿到前后两个ItemView的实例,然后根据滑动时调用的方法onPageScrolled()中设置动画
 * 1.在MyViewPager中创建对应位置的ItemView的保存实力HashMap<position,View/>
 * 2.在ViewPager的Adapter中的创建方法instantiateItem中,设置
 */
public class AnimationViewPagerActivity extends BaseActivity {

    private static final java.lang.String TAG = AnimationViewPagerActivity.class.getSimpleName();
    private AnimationViewPager mViewPager;
    private int[] images = new int[]{R.mipmap.ic_7,
            R.mipmap.ic_10, R.mipmap.ic_17, R.mipmap.img_beauti};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_view_pager);
        initToolBar();

        mViewPager = (AnimationViewPager) findViewById(R.id.vp_viewPage);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Logger.d(TAG, "--instantiateItem--position:" + position);
                //每个Item使用ImageView
                ImageView imageView = new ImageView(AnimationViewPagerActivity.this);
                imageView.setImageResource(images[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);

                mViewPager.setObjectForPosition(position,imageView);

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                Logger.d(TAG, "--destroyItem--position:" + position);
                container.removeView((View) object);
            }
        });
    }
}
