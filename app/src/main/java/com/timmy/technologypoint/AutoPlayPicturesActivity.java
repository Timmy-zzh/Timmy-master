package com.timmy.technologypoint;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/23.
 * 自动轮播图
 * 实现功能:根据手势滑动的ViewPager图片,和点的容器随图片的滑动而改变
 * 设置ViewPager图片数据(本地数据-网络获取),设置点的数据
 */
public class AutoPlayPicturesActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "AutoPlayPicturesActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
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
    private AutoSwitchPicTask mAutoSwitchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_pictures);
        ButterKnife.bind(this);
        initToolBar();
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);

            if (i != 0) {
                params.leftMargin = 20;//除了第一个其他点容器间隔10
            } else {
                //默认第一个选中
                point.setBackgroundResource(R.drawable.point_selected);
            }
            ll_pointCon.addView(point, params);
        }

        //为ViewPager设置适配器
        vp_viewPager.setAdapter(new PicturePagerAdapter());

        //给ViewPager设置滑动监听
        vp_viewPager.addOnPageChangeListener(this);

        //设置自动轮播功能
        if (mAutoSwitchTask == null) {
            mAutoSwitchTask = new AutoSwitchPicTask();
        }
        mAutoSwitchTask.start();

        //设置ViewPage的滑动监听-->当手放上去的时候,由手势控制自动轮播
        vp_viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 如果手指按下去时，希望轮播停止，
                        mAutoSwitchTask.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // 如果手指抬起时，图片进行轮播
                        mAutoSwitchTask.start();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    public class PicturePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        //用来判断是否有预加载
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //ViewPager初始化-->position标示第几个页面
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

//            position = position % mData.size();

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

    class AutoSwitchPicTask extends Handler implements Runnable {
        /**
         * 开启任务
         */
        public void start() {
            stop();
            postDelayed(this, 2000);
        }

        /**
         * 关闭任务
         */
        public void stop() {
            removeCallbacks(this);
        }

        @Override
        public void run() {
            // ViewPager选中下一个，如果是最后一个就选中第一个

            int position = vp_viewPager.getCurrentItem();
            if (position != vp_viewPager.getAdapter().getCount() - 1) {
                // 选中下一个
                vp_viewPager.setCurrentItem(++position);
            } else {
                // 如果是最后一个就选中第一个
                vp_viewPager.setCurrentItem(0);
            }
            // 发送延时任务
            postDelayed(this, 2000);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
