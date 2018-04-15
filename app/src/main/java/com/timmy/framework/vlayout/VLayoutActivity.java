package com.timmy.framework.vlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.timmy.R;
import com.timmy.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class VLayoutActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String, Object>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout);
        initToolBar();
        initView();
    }

    /**
     * 设置RecyclerView首页样式
     */
    private void initView() {
        //1.创建RecyclerView,VirtualLayoutManager 并进行绑定
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycleView);
        //垂直方向的LayoutManager
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //2.设置回收池大小
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0,10);

        /**
         * 3.设置Adapter
         *      a.继承自DelegateAdapter-->V_layout专门为管理layouthelper定制的adapter
         *      b.继承自VirtualLayoutAdapter-->当需要实现复杂需求时选用
         */
        //使用DelegateAdapter实现--true-->不论是不是属于同一个子adapter，相同类型的item都能复用
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager,true);
        mRecyclerView.setAdapter(delegateAdapter);

        //获取数据
        /**
         * 步骤3:设置需要存放的数据
         * */
        listItem = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemImage", R.mipmap.ic_launcher);
            listItem.add(map);
        }

        /**
         * 4.往DelegateAdapter中添加不同类型的adapter
         */
        //使用链表-->因为这个集合会涉及元素的删除和增加
        List<DelegateAdapter.Adapter> adapterList = new LinkedList<>();
        {
            //各种布局
            /**
             * a.设置线性布局
             */
            //创建LayoutHelper
            LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
            /**
             * 设置LayoutHelper属性
             */
            //设置布局里item个数
            linearLayoutHelper.setItemCount(5);
            //设置内边距
            linearLayoutHelper.setPadding(10, 20, 5, 5);
            //设置外边距
            linearLayoutHelper.setMargin(5, 10, 15, 20);
            //设置背景色
            linearLayoutHelper.setBgColor(Color.RED);
            //设置每个item布局的宽与高的比
            linearLayoutHelper.setAspectRatio(5);

            //设置每行item的距离
            linearLayoutHelper.setDividerHeight(10);
            adapterList.add(new MyDelegateAdapter(this, linearLayoutHelper, 5, listItem) {
                @Override
                public void onBindViewHolder(MainViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                }
            });
        }

        {
            /**
             * b.网格布局
             */
            //构造函数里设置每行的网格个数
            GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
            //公共属性
            gridLayoutHelper.setItemCount(13);
            gridLayoutHelper.setPadding(10, 10, 10, 10);
            gridLayoutHelper.setMargin(20, 20, 20, 20);
            gridLayoutHelper.setBgColor(Color.BLUE);
            gridLayoutHelper.setAspectRatio(5);

            //GridLayoutHelper特有属性
//        gridLayoutHelper.setWeights(new float[]{40,30,30});//设置每行中每个网格的宽度占比
            gridLayoutHelper.setWeights(new float[]{40, 30, 20});//设置每行中每个网格的宽度占比
            gridLayoutHelper.setVGap(20);//设置垂直间距
            gridLayoutHelper.setHGap(10);//水平间距
            gridLayoutHelper.setAutoExpand(false);//是否自动填充空白区域
            gridLayoutHelper.setSpanCount(3);//设置每行多少个网格
            // 通过自定义SpanSizeLookup来控制某个Item的占网格个数
//        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position > 2) {
//                    return 2;
//                    // 第2个位置后,每个Item占2个网格
//                } else {
//                    return 1;
//                    // 第2个位置前,每个Item占1个网格
//                }
//            }
//        });
            adapterList.add(new MyDelegateAdapter(this, gridLayoutHelper, 13, listItem));
        }

        {
            /**
             * c.固定布局
             */
            //位置在左上角
            FixLayoutHelper fixLayoutHelper = new FixLayoutHelper(FixLayoutHelper.TOP_LEFT, 40, 40);
//        fixLayoutHelper.setItemCount(1);
            //设置公共属性
            fixLayoutHelper.setBgColor(Color.GREEN);

            //设置特有属性
//        fixLayoutHelper.setAlignType(FixLayoutHelper.TOP_LEFT);
//        fixLayoutHelper.setX(30);
//        fixLayoutHelper.setY(50);
            VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(VirtualLayoutManager.LayoutParams.MATCH_PARENT, 150);
            adapterList.add(new MyDelegateAdapter(this, fixLayoutHelper, 1, layoutParams, listItem));
        }

        {
            /**
             * d.可显示的固定布局-->使用场景:返回到首页
             */
            ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.BOTTOM_RIGHT, 20, 20);
            scrollFixLayoutHelper.setItemCount(1);
            scrollFixLayoutHelper.setBgColor(Color.YELLOW);
            //特有属性
            scrollFixLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);
            scrollFixLayoutHelper.setX(50);
            scrollFixLayoutHelper.setY(20);
            scrollFixLayoutHelper.setShowType(ScrollFixLayoutHelper.SHOW_ON_ENTER);
            VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(VirtualLayoutManager.LayoutParams.MATCH_PARENT, 150);
            adapterList.add(new MyDelegateAdapter(this, scrollFixLayoutHelper, 1, layoutParams,listItem));
        }

        {
            /**
             * e.浮动布局:可随意拖动,但最终会吸附到两侧
             */
            FloatLayoutHelper floatLayoutHelper = new FloatLayoutHelper();
            floatLayoutHelper.setItemCount(1);
            //特有属性
            floatLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);
            floatLayoutHelper.setDefaultLocation(0, 300);
            VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(100, 100);
            adapterList.add(new MyDelegateAdapter(this, floatLayoutHelper, 1, layoutParams,listItem));
        }

        {
            /**
             * f.栏格布局:该布局只设有一栏,只有一行的线性布局
             */
            ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();
            columnLayoutHelper.setAspectRatio(4);
            columnLayoutHelper.setWeights(new float[]{30,40,30});
            adapterList.add(new MyDelegateAdapter(this, columnLayoutHelper, 1, listItem));
        }

        /**
         * g.通栏布局（SingleLayoutHelper）
         * 布局只有一栏，该栏只有一个Item
         */

        {
            /**
             * h.一拖N布局 （OnePlusNLayoutHelper）
             */
            //1拖4布局
            OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
            onePlusNLayoutHelper.setColWeights(new float[]{40});
            onePlusNLayoutHelper.setRowWeight(50);
            onePlusNLayoutHelper.setAspectRatio(2);
            adapterList.add(new MyDelegateAdapter(this, onePlusNLayoutHelper, 4, listItem));
        }

        {
            /**
             * i.自定义布局
             */
        }

        //最后设置DelegateAdapter.setAdapter
        delegateAdapter.setAdapters(adapterList);

    }
}
