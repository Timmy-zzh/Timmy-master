package com.timmy.rxjava.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timmy.rxjava.R;
import com.timmy.rxjava.model.AppInfo;
import com.timmy.rxjava.ui.adapter.AppInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * RxJava的实现--使用响应式方式获取本地所有安装的应用的数据,
 * 获取数据后在RecycleView界面中进行展示
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.srl_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.rv_recycle_view)
    RecyclerView mRecyclerView;
    private List<AppInfo> apps = new ArrayList<>();
    private AppInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initRecycleView();
        initData();
    }

    private void initView() {
        mRecyclerView.setVisibility(View.GONE);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_purple,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //显示刷新进度条
        mSwipeRefreshLayout.setRefreshing(true);

    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new AppInfoAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
//        AppInfo appInfo = null;
//        for (int i = 0; i < 10; i++) {
//            appInfo = new AppInfo("AppName" + i, "", 90);
//            listData.add(appInfo);
//        }
//        adapter.setData(listData);
//        mRecyclerView.setVisibility(View.VISIBLE);


    }


    private Observable<AppInfo> getApps() {
        return Observable.create(new Observable.OnSubscribe<AppInfo>() {

            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {

            }
        });
    }


    //下拉刷新
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /*****************************************************************
     * RxJava练习
     * 生产者Observables和Subjects, 消费者Observer和Subscriber
     */

    /**
     * 创建一个Observable
     */
    public void createObservable() {

        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

            }
        });
    }

}
