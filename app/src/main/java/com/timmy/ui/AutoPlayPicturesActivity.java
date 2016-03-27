package com.timmy.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.timmy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/23.
 * 自动轮播图
 * 实现功能:根据手势滑动的ViewPager图片,和点的容器随图片的滑动而改变
 * 设置ViewPager图片数据(本地数据-网络获取),设置点的数据
 */
public class AutoPlayPicturesActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    //    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.vp_viewPage)
    ViewPager vp_viewPager;
    @Bind(R.id.ll_point_container)
    LinearLayout ll_pointCon;
    //图片资源-本地
    private int[] imageData = new int[]{R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e};
    //图片数据放到List中,-->可以使用imageData进行数据的存放
    private List<Integer> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_pictures);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //模拟网络数据,真实数据存放的是Image
        mData = new ArrayList<Integer>();
        for (int i = 0; i < imageData.length; i++) {
            mData.add(imageData[i]);

            //添加点的容器
            View point = new View(this);
            point.setBackgroundResource(R.drawable.point_normal);

            //设置单个点的属性-->使用LinearLayout作为点容器的属性-->设置左右间隔
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);

            if (i != 0) {
                params.leftMargin = 10;//除了第一个其他点容器间隔10
            } else {
                //默认第一个选中
                point.setBackgroundResource(R.drawable.point_selected);
            }
            ll_pointCon.addView(point,params);
        }

        //为ViewPager设置适配器
        vp_viewPager.setAdapter(new PicturePagerAdapter());

        //给ViewPager设置滑动监听
        vp_viewPager.addOnPageChangeListener(this);

        //设置当前选中的页码-->为实现无限轮播,当前位置需要一个很大的数字
        //当时第一个还是要默认是第一个的
        int middle = Integer.MAX_VALUE / 2;
        int extra = middle % mData.size();
        int currentPoint = middle - extra;
        vp_viewPager.setCurrentItem(currentPoint);
    }


    public class PicturePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mData != null ? Integer.MAX_VALUE : 0;
        }

        //用来判断是否有预加载
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //ViewPager初始化-->position标示第几个页面
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position = position % mData.size();

            ImageView imageView = new ImageView(AutoPlayPicturesActivity.this);
            imageView.setImageResource(mData.get(position));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //相当于 vp_viewPager.addView();
            container.addView(imageView);

            return imageView;
        }

        //页面销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    // onPageScrolled : 当Pager滑动时的回调
    // @position: 滑动起始的页面
    // @positionOffset: 拖动的百分比 （0-1）
    // @positionOffsetPixels: 拖动的距离
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 当Pager固定时的回调-->设置当前那个点选中
    // position:当前固定的页面
    @Override
    public void onPageSelected(int position) {

        //获取点容器的数据
        int childCount = ll_pointCon.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = ll_pointCon.getChildAt(i);
            childAt.setBackgroundResource(i == position ? R.drawable.point_selected
                    : R.drawable.point_normal);
        }
    }

    // pager状态改变时的回调-->滑动时数据就不去加载
    // state : 状态值
    // * @see ViewPager#SCROLL_STATE_IDLE : idle空闲
    // * @see ViewPager#SCROLL_STATE_DRAGGING ： 拖动状态
    // * @see ViewPager#SCROLL_STATE_SETTLING : settling固定状态
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
