package com.timmy.highUI.motionEvent.qqHome;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.nineoldandroids.view.ViewHelper;
import com.timmy.library.util.Logger;

/**
 * Created by admin on 2017/2/20.
 * 高仿手机QQ主界面：
 * 1.获取到主界面和左侧侧滑界面的控件对象-默认排放主界面
 * 2.滑动事件：滑动时动画显示隐藏
 * 3.TouchEvent中UP事件处理：弹回效果实现
 */
public class QQHomeSlidLayout extends HorizontalScrollView {

    private String TAG = "QQHomeSlidLayout";
    private boolean isOnce;
    private int screenWidth;
    private ViewGroup menuView;
    private ViewGroup mainView;
    private int menuPaddingRight = 200;
    private int menuWidth;
    private float downX;
    private boolean isMenuOpen;

    public QQHomeSlidLayout(Context context) {
        this(context, null);
    }

    public QQHomeSlidLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQHomeSlidLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取屏幕宽度
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isOnce) {
            //获取主页和侧滑控件对象
            ViewGroup childViewRoot = (ViewGroup) getChildAt(0);//ScrollView一级子类-LinearLayout
            menuView = (ViewGroup) childViewRoot.getChildAt(0);
            mainView = (ViewGroup) childViewRoot.getChildAt(1);
            //设置左侧侧滑和主页面的宽度
            menuWidth = screenWidth - menuPaddingRight;
            menuView.getLayoutParams().width = menuWidth;
            mainView.getLayoutParams().width = screenWidth;
        }
    }

    //默认显示主界面 changed有什么作用？
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isOnce) {
            scrollTo(menuWidth, 0);
            isOnce = true;
        }
    }

    /**
     * 处理Touch事件
     * 1.down事件获取点击位置
     * 2.up事件处理弹回效果
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                float scrollX = ev.getX() - downX;
                Logger.d(TAG, "scrollX:" + scrollX+",isMenuOpen:"+isMenuOpen);

                if (Math.abs(scrollX) < 5 && isMenuOpen) {
                    Logger.d(TAG, "111111scrollX:" + scrollX);
                    openMenuLayout(false);
                    return true;
                }

                //往右滑动
                if (scrollX > 0) {
                    if (scrollX > menuWidth / 3) {
                        openMenuLayout(true);
                    } else {
                        openMenuLayout(false);
                    }
                } else if (scrollX < 0) {
                    //往左滑动
                    if (-scrollX > menuWidth / 3) {
                        openMenuLayout(false);
                    } else {
                        openMenuLayout(true);
                    }
                }
                //消费
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void openMenuLayout(boolean open) {
        if (open){
            smoothScrollTo(0, 0);
            isMenuOpen = true;
        }else{
            smoothScrollTo(menuWidth, 0);
            isMenuOpen = false;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //滑动因子
        float factor = (float) l / menuWidth;// 侧边栏全部展开为0
        Logger.d(TAG, "factor:" + factor + ",left:" + l);
        //左侧滑动比主界面慢  --ViewHelper nineOldAndroid-兼容
        ViewHelper.setTranslationX(menuView, l * 0.7f);
        //缩放
        float leftScale = 1 - 0.4f * factor; //0.4-1
        ViewHelper.setScaleX(menuView, leftScale);
        ViewHelper.setScaleY(menuView, leftScale);

        float rightScale = 0.8f + 0.2f * factor;
        ViewHelper.setScaleX(mainView, rightScale);
        ViewHelper.setScaleY(mainView, rightScale);
        //透明度
        float alpha = 1 - factor;
        ViewHelper.setAlpha(menuView, alpha);
    }
}
