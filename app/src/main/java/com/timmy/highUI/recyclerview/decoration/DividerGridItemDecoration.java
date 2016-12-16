package com.timmy.highUI.recyclerview.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.timmy.library.util.Logger;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * RecyclerView网格布局的分割线－需要同时绘制水平和垂直方向的
 * 网格布局包括Grid网格和瀑布流网格
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private String TAG = this.getClass().getSimpleName();
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private Drawable mDivider;

    public DividerGridItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    /**
     * 水平方向分割线
     * @param c
     * @param parent
     */
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (isLastRow(parent, i, getSpanCount(parent), childCount))//最后一行不绘制
                continue;

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isLastColumn(parent, i, getSpanCount(parent), childCount))//最后一列不绘制
                continue;

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();

            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();
//        int currChildPos = getCurrentChildPosition(parent, view);
//        int spanCount = getSpanCount(parent);
//        int itemCount = parent.getAdapter().getItemCount();
//        Logger.d(TAG, "currChildPos:" + currChildPos + " - spanCount:" + spanCount + " - itemCount:" + itemCount);
//        if (isLastRow(parent, currChildPos, spanCount, itemCount)) {
//            bottom = 0;
//        }
//        if (isLastColumn(parent, currChildPos, spanCount, itemCount)) {
//            right = 0;
//        }
//        Logger.d(TAG, "getItemOffsets:" + currChildPos + " - right:" + right + " - bottom:" + bottom);
        outRect.set(0, 0, right, bottom);
    }

    /**
     * 是否是最后一列
     *
     * @param parent
     * @param currChildPos
     * @param spanCount
     * @param itemCount
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int currChildPos, int spanCount, int itemCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null && layoutManager instanceof GridLayoutManager) {
            if ((currChildPos + 1) % spanCount == 0) {
                return true;
            }
        } else if (layoutManager != null && layoutManager instanceof StaggeredGridLayoutManager) {
            //瀑布流也有两种方向
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int orientation = staggeredGridLayoutManager.getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {//垂直
                if ((currChildPos + 1) % spanCount == 0) {
                    return true;
                }
            } else {
                if (itemCount % spanCount != 0) {
                    itemCount = itemCount - itemCount % spanCount;
                    if (currChildPos >= itemCount) {//这种判断方式在item最后一行未填满的情况下可行
                        return true;
                    }
                } else {//最后一行填满的情况处理
                    itemCount = itemCount - spanCount;
                    if (currChildPos >= itemCount) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 是否是最后一行
     *
     * @param parent
     * @param currChildPos
     * @param spanCount
     * @param itemCount
     * @return
     */
    private boolean isLastRow(RecyclerView parent, int currChildPos, int spanCount, int itemCount) {
        boolean result = false;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null && layoutManager instanceof GridLayoutManager) {
            if (itemCount % spanCount != 0) {
                itemCount = itemCount - itemCount % spanCount;
                if (currChildPos >= itemCount) {//这种判断方式在item最后一行未填满的情况下可行
                    return true;
                }
            } else {//最后一行填满的情况处理
                itemCount = itemCount - spanCount;
                if (currChildPos >= itemCount) {
                    return true;
                }
            }
        } else if (layoutManager != null && layoutManager instanceof StaggeredGridLayoutManager) {
            //瀑布流也有两种方向
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int orientation = staggeredGridLayoutManager.getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {//垂直

                if (itemCount % spanCount != 0) {
                    itemCount = itemCount - itemCount % spanCount;
                    if (currChildPos >= itemCount) {//这种判断方式在item最后一行未填满的情况下可行
                        return true;
                    }
                } else {//最后一行填满的情况处理
                    itemCount = itemCount - spanCount;
                    if (currChildPos >= itemCount) {
                        return true;
                    }
                }
            } else {
                if (currChildPos % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前item在recycler的adapter的位置
     *
     * @param parent
     * @param view
     * @return
     */
    private int getCurrentChildPosition(RecyclerView parent, View view) {
        return parent.getChildAdapterPosition(view);
    }

    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            spanCount = gridLayoutManager.getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            spanCount = staggeredGridLayoutManager.getSpanCount();
        }
        return spanCount;
    }
}

