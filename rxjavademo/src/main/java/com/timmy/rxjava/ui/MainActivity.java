package com.timmy.rxjava.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timmy.rxjava.BuildConfig;
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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava的实现--使用响应式方式获取本地所有安装的应用的数据,
 * 获取数据后在RecycleView界面中进行展示
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.srl_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.rv_recycle_view)
    RecyclerView mRecyclerView;
    private ArrayList<AppInfo> apps = new ArrayList<>();
    private List<AppInfo> listData = new ArrayList<>();
    private AppInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setStrictMode();
        initView();
        initRecycleView();
        initData();
    }

    private void setStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    private void initView() {
        mRecyclerView.setVisibility(View.GONE);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_purple,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new AppInfoAdapter(this);

    }

    private void initData() {
        //显示刷新进度条
        mSwipeRefreshLayout.setRefreshing(true);
        //将应用信息放到一个集合中
//        Observable<List<AppInfo>> listObservable = getApps().toSortedList();
        Observable<List<AppInfo>> listObservable = getApps();
        /**
         * 通过被观察者,使被观察者被订阅出去,生成观察者
         * subscribeOn(Schedulers.io())为被观察者添加调度器--使被观察者在IO线程中执行,不会在UI线程执行--刷新界面就不行了
         * -->所以我们需要切换到主线程进行界面刷新--使用 observeOn(AndroidSchedulers.mainThread())方法
         *
         * ***********************
         * observeOn() 方法可以在一个指定的调度器上提供结果
         */
        listObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onCompleted--观察者收到通知");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("onError--出错");
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        LogUtil.d("onNext----" + appInfos.size());
                        adapter.setData(appInfos);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView.setAdapter(adapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }


    //获取手机安装的所有应用数据-将这些数据放在一个被观察者中
    private Observable<List<AppInfo>> getApps() {
        Observable<List<AppInfo>> appInfoObservable = Observable.create(new Observable.OnSubscribe<List<AppInfo>>() {

            @Override
            public void call(Subscriber<? super List<AppInfo>> subscriber) {
                List<AppInfo> appInfoList = new ArrayList<AppInfo>();
                List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
                //解析单个应用信息
                for (PackageInfo packageInfo : packages) {
                    AppInfo appInfo = new AppInfo();
                    //拿到Application的Name
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    appInfo.setAppName(appName);
                    appInfo.setPackageName(packageInfo.packageName);
                    appInfo.setVersionName(packageInfo.versionName);
                    appInfo.setVersionCode(packageInfo.versionCode);
                    Drawable appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                    appInfo.setAppIcon(appIcon);
                    //apps 中获取的手机上安装的所有应用信息,可以通过方法判断该应用是否是系统应用
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        //非系统应用
                        appInfo.setSystem(false);
                    } else {
                        appInfo.setSystem(true);
                    }
                    appInfoList.add(appInfo);
                }
                //获取到一个应用的数据了,就去调用通知观察者
                subscriber.onNext(appInfoList);
                //获取应用数据完成,通知
                if (!subscriber.isUnsubscribed())
                    subscriber.onCompleted();
            }
        });
        return appInfoObservable;
    }

    //获取手机安装的所有应用数据-将这些数据放在一个被观察者中
    private Observable<AppInfo> getApp() {
        Observable<AppInfo> appInfoObservable = Observable.create(new Observable.OnSubscribe<AppInfo>() {

            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {
                List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
                //解析单个应用信息
                for (PackageInfo packageInfo : packages) {
                    AppInfo appInfo = new AppInfo();
                    //拿到Application的Name
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    appInfo.setAppName(appName);
                    appInfo.setPackageName(packageInfo.packageName);
                    appInfo.setVersionName(packageInfo.versionName);
                    appInfo.setVersionCode(packageInfo.versionCode);
                    Drawable appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                    appInfo.setAppIcon(appIcon);
                    //apps 中获取的手机上安装的所有应用信息,可以通过方法判断该应用是否是系统应用
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        //非系统应用
                        appInfo.setSystem(false);
                    } else {
                        appInfo.setSystem(true);
                    }
                    //获取到一个应用的数据了,就去调用通知观察者
                    subscriber.onNext(appInfo);
                }
                //获取应用数据完成,通知
                if (!subscriber.isUnsubscribed())
                    subscriber.onCompleted();
            }
        });
        return appInfoObservable;
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
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
     * <p/>
     * *******************操作符:**********************
     * from();从一个列表中创建一个Observable,这个Observable会发射每一个元素
     * just(xxx);有多少个参数,发射多少次
     * repeat(),重复发射
     * defer();延迟创建
     * range(a,b);从位置a开发发射b个数量的
     * interval(i,second);轮询,两次发射中间相隔i时间,second是单位
     * timer();在一定的时间间隔后发射
     * <p/>
     * *************过滤Observables************
     * filter()->fun通过条件过滤
     * take(2);获取我们需要的部分数据,最前面的2个
     * takeLast(3);获取最后的3个元素
     * distinct();去重数据,有重复发射--就有去重发射
     * <p/>
     * first(),last();发射第一个和最后一个数据
     * skip();skipLast();跳过开头的n个元素,或者跳过结束的n个元素
     * elementAt();只发射第n个元素
     * sample(30,Seconds);每隔30秒发射一次
     * Timeout();时间约束;每两秒获取一个值
     * <p/>
     * *****************转换Observables*****************
     * Map家族
     * map()-Func1();可在func1方法中处理数据
     * FlatMap();
     * Scan()
     * GroupBy();
     * buffer();
     * Cast();
     * <p/>
     * *****************组合Observables****************
     * merger();合并两个Oservable
     * Zip();将两个或两个以上的Observables发射的item合并.
     * Join();关注时间因素
     * <p/>
     * *******************调度器**************************
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

        //创建观察者--被观察者发射这些值,一个一个的发射--操作符from
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
