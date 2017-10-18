package com.timmy.framework.netFw;

import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.framework.annotationRuntime.ViewInjectUtils;
import com.timmy.framework.annotationRuntime.annotations.ContentView;
import com.timmy.framework.annotationRuntime.annotations.OnViewClick;
import com.timmy.framework.annotationRuntime.annotations.ViewInject;
import com.timmy.framework.netFw.http.MyVolley;
import com.timmy.framework.netFw.http.listener.IDataListener;
import com.timmy.framework.netFw.model.GankResult;
import com.timmy.framework.netFw.utils.AsyncNetUtils;
import com.timmy.framework.netFw.utils.NetWorkCallback;
import com.timmy.library.util.Logger;
import com.timmy.library.util.Toast;

@ContentView(R.layout.activity_net_work)
public class NetWorkRequestActivity extends BaseActivity {

    //    private static final String URL_BAIDU = "http://www.baidu.com";
    private static final String URL_BAIDU = "http://gank.io/api/data/Android/10/1";
    private static final String URL_GANK = "http://gank.io/api/data/Android/10/1";
    @ViewInject(R.id.tv_result)
    TextView mResult;//网络访问请求结果展示

    @ViewInject(R.id.ll_reault)
    LinearLayout mResultCon;//

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_net_work);

        ViewInjectUtils.inject(this);
        initToolBar();
    }

    @OnViewClick({R.id.btn_normal, R.id.btn_framework})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_normal:
                networkRequestNarmal();
                break;
            case R.id.btn_framework:
                networkRequestFramework();
                break;
        }
    }

    private void networkRequestNarmal() {
        Toast.toastShort("普通网络请求");
        //同步请求-->报错NetworkOnMainThreadException
//        String str = NetUtils.get(URL_BAIDU);
//        mResult.setText(str);

        //在子线程中执行网络请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final String str = NetUtils.get(URL_BAIDU);
//                //通过handler切换线程，让更新ui的操作在主线程中执行
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mResult.setText(str);
//                    }
//                });
//            }
//        }).start();

        //异步＋接口回调
        AsyncNetUtils.get(URL_BAIDU, new NetWorkCallback() {
            @Override
            public void onSuccess(String response) {
                Logger.d(response);
                mResult.setText(response);
            }

            @Override
            public void onFail() {

            }
        });
    }

    /**
     * 自己写的网络框架实现网络请求
     */
    private void networkRequestFramework() {
        Toast.toastShort("网络框架请求");
        for (int i = 0; i < 50; i++) {
            final int finalI = i;
            MyVolley.sendRequest(null, URL_GANK, GankResult.class, new IDataListener<GankResult>() {
                @Override
                public void onSuccess(GankResult gankResult) {
                    TextView textView = new TextView(NetWorkRequestActivity.this);
                    textView.setLines(1);
                    textView.setTextColor(Color.BLUE);
                    String msg = gankResult.toString();
                    Logger.d("Item:"+ finalI +"--"+msg);
                    textView.setText("Item:"+ finalI +"--"+msg);
                    mResultCon.addView(textView);
                }

                @Override
                public void onError(int state) {

                }
            });
        }
    }
}
