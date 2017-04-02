package com.timmy.framework.netFw;

import android.os.Handler;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.framework.annotationsFramework.ViewInjectUtils;
import com.timmy.framework.annotationsFramework.annotations.ContentView;
import com.timmy.framework.annotationsFramework.annotations.OnViewClick;
import com.timmy.framework.annotationsFramework.annotations.ViewInject;
import com.timmy.framework.netFw.utils.AsyncNetUtils;
import com.timmy.framework.netFw.utils.NetUtils;
import com.timmy.framework.netFw.utils.NetWorkCallback;
import com.timmy.library.util.Toast;

@ContentView(R.layout.activity_net_work)
public class NetWorkRequestActivity extends BaseActivity {

//    private static final String URL_BAIDU = "http://www.baidu.com";
    private static final String URL_BAIDU = "http://gank.io/api/data/Android/10/1";
    @ViewInject(R.id.tv_reault)
    TextView mResult;//网络访问请求结果展示
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_net_work);

        ViewInjectUtils.inject(this);
        initToolBar();

    }

    @OnViewClick({R.id.btn_normal,R.id.btn_framework})
    public void onButtonClick(View view){
        switch (view.getId()){
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
                mResult.setText(response);
            }

            @Override
            public void onFail() {

            }
        });

    }

    private void networkRequestFramework() {
        Toast.toastShort("网络框架请求");


    }
}
