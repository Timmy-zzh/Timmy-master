package com.timmy.advance.slidelist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.timmy.R;
import com.timmy.util.Logger;

/**
 * 自定义向右滑动删除Item的ListView
 * 步骤:
 * 1.初始化数据,布局,获取宽高数值--删除按钮使用PopuWindow
 * 2.获取ListView的item
 * 3.监听点击事件,当在listView的Item向右滑动时,显示删除按钮,
 * 4.为点击按钮添加点击的监听事件,设置回调方法.
 */
public class SlideDeleteListView extends ListView {

    private final String TAG = this.getClass().getSimpleName();
    private final LayoutInflater layoutInflater;
    private final int touchSlop;//用户滑动的最小距离--8
    private final Button mDeleteBtn;
    private final PopupWindow mPopupWindow;
    private final int mPwHeight;
    private final int mPwWidth;
    private int xDown;//手指按下时的x坐标
    private int yDown;
    private int mCurrItemViewPos;
    private View mCurrItemView;
    private int xMove;//手指移动的x坐标
    private int yMove;
    private boolean isSlideing;//标记当前手势正在从右向左滑动
    private OnDeleteButtonClickListener mListener;

    /**
     * 1.初始化数据
     *
     * @param context
     * @param attrs
     */
    public SlideDeleteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d("SlideDeleteListView");

        layoutInflater = LayoutInflater.from(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//        touchSlop = 30;

        //获取按钮布局,创建pw
        View deleteView = layoutInflater.inflate(R.layout.view_delete_btn, null);
        mDeleteBtn = (Button) deleteView.findViewById(R.id.btn_delete);
        mPopupWindow = new PopupWindow(deleteView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        int mPwHeightPre = mPopupWindow.getContentView().getMeasuredHeight();
        int mPwWidthPre = mPopupWindow.getContentView().getMeasuredWidth();
        Logger.d("测量前的宽高--" + mPwWidthPre + "--" + mPwHeightPre);
        //测量
        mPopupWindow.getContentView().measure(0, 0);
        mPwHeight = mPopupWindow.getContentView().getMeasuredHeight();
        mPwWidth = mPopupWindow.getContentView().getMeasuredWidth();
        Logger.d("测量后的宽高--" + mPwWidth + "--" + mPwHeight);
    }


    /**
     * 事件分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Logger.d("SlideDeleteListView--dispatchTouchEvent");
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        Logger.d("ev--x--" + x + "--y--" + y);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //按下
                xDown = x;
                yDown = y;
                //如果当前PW是显示状态,则直接隐藏,并且屏蔽ListView的touch事件
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                    return false;
                }
                //获取当前手指按下时位置的ListView的Item位置
                mCurrItemViewPos = pointToPosition(xDown, yDown);
                View itemView = getChildAt(mCurrItemViewPos - getFirstVisiblePosition());
                mCurrItemView = itemView;
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                //手指移动的距离
                int dx = xMove - xDown;
                int dy = yMove - yDown;
                //判断是否是从右向左滑动
                if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    isSlideing = true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 点击事件处理-是否消费
     * 如果是从右向左滑动才消费该事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Logger.d("--onTouchEvent");
        int action = ev.getAction();
        if (isSlideing) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    int[] location = new int[2];
                    //获取ListView当前Item的位置x-y
                    mCurrItemView.getLocationOnScreen(location);
                    //设置PW的显示
                    mPopupWindow.update();
                    mPopupWindow.showAtLocation(mCurrItemView, Gravity.LEFT | Gravity.TOP,
                            location[0] + mCurrItemView.getWidth(),
                            location[1] + mCurrItemView.getHeight() / 2 - mPwHeight / 2);
                    //设置删除按钮的回调
                    mDeleteBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.deleteBtn(mCurrItemViewPos);
                            mPopupWindow.dismiss();
                        }
                    });
                    break;
                case MotionEvent.ACTION_UP:
                    isSlideing = false;
            }
            //相应滑动期间屏幕ItemClick事件,避免发生冲突
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public interface OnDeleteButtonClickListener {
        void deleteBtn(int position);
    }

    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        this.mListener = listener;
    }

}
