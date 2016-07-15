package com.timmy.rxjava.ui;

import android.content.Intent;
import android.content.pm.ResolveInfo;
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
import com.timmy.rxjava.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

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
    private List<AppInfo> listData = new ArrayList<>();
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
        AppInfo appInfo = null;
        for (int i = 0; i < 10; i++) {
            appInfo = new AppInfo("AppName" + i, "", 90);
            listData.add(appInfo);
        }
        adapter.setData(listData);
        mRecyclerView.setVisibility(View.VISIBLE);

//        createObservable();

        getApps();

    }

    //获取手机安装的所有应用数据
    private Observable<AppInfo> getApps() {
//        return Observable.create(new Observable.OnSubscribe<AppInfo>() {
//
//            @Override
//            public void call(Subscriber<? super AppInfo> subscriber) {
//
//                List<AppInfoRich> apps = new ArrayList<AppInfoRich>();
//                Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
//                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//                //根据Intent的条件搜索Activity
//                List<ResolveInfo> infos = getPackageManager().queryIntentActivities(mainIntent, 0);
//                for (ResolveInfo info :infos){
//                    apps.add(new AppInfoRich(MainActivity.this,info));
//                }
//
//                for (ResolveInfo info :infos){
//                    info.icon;
//                }
//
//            }
//        });

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //根据Intent的条件搜索Activity
        List<ResolveInfo> infos = getPackageManager().queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info : infos) {
           LogUtil.d("icon--"+info.icon);
           LogUtil.d("getIconResource--"+info.getIconResource());
           LogUtil.d("activityInfo.name--"+info.activityInfo.name);
           LogUtil.d("resolvePackageName--"+info.resolvePackageName);
        }


        return null;
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
     * 创建一个Observable被观察者--在有观察者订阅我们的Observable时,执行call方法.
     * <p/>
     * OnSubscribe是一个接口,继承自Aciton1--内部定义了call回调方法
     * call方法传入了参数-Subscriber消费者
     */
    public void createObservable() {

        Observable<Object> observableObj = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                LogUtil.d("call方法调用");
            }
        });

        final Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });

        //Observable.from()
        List<Integer> items = new ArrayList<Integer>();
        items.add(1);
        items.add(10);
        items.add(100);
        items.add(200);

        //创建观察者--被观察者发射这些值,一个一个的发射
        Observable<Integer> observableInt = Observable.from(items);
        //订阅
        Subscription subscription = observableInt.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                LogUtil.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("Throwable");
            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.d("onNext--" + integer);
            }
        });


        //创建被观察者
        Observable<String> observableStr = Observable.just(getStr());
        //订阅
        Subscription subscriptionStr = observableStr.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtil.d("onCompleted--str");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("onCompleted--Throwable");
            }

            @Override
            public void onNext(String s) {
                LogUtil.d("onNext--" + s);
            }
        });
    }

    private String getStr() {
        return "a String obj";
    }

}
