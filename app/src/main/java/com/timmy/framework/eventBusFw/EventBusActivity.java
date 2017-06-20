package com.timmy.framework.eventBusFw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.framework.eventBusFw.EventBus.Subscribe;
import com.timmy.framework.eventBusFw.EventBus.ThreadMode;
import com.timmy.framework.eventBusFw.EventBus.TimmyEventBus;
import com.timmy.library.util.Logger;

/**
 * EventBus事件总栈
 * 1.观察者模式,注册->通知 -> 反注册
 * 2.通过注册register将该Activity以及其父类中添加了订阅注解的方法收集起来放到一个map中,
 * 并且将该方法,注解设置的线程模式和参数进行封装
 * 3.在其他界面通过post()方法发送通知,让注册了EventBus的方法,根据参数进行判断
 * 4.线程判断,并进行线程切换.
 */
public class EventBusActivity extends BaseActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        initToolBar();
        button = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.tv_content);
        button.setText("界面跳转");
        TimmyEventBus.getDefault().register(this);
    }

    public void jump(View view){
        startActivity(new Intent(this,SendEventActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.PostThread)
    public void receiveEventPost(Person timmy){
        Logger.d(" PostThread -- "+timmy.getName()+",thread:"+Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void receiveEventBack(Person timmy){
        Logger.d(" BackgroundThread -- "+timmy.getName()+",thread:"+Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void receiveEventMain(Person timmy){
        Logger.d(" MainThread -- "+timmy.getName()+",thread:"+Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimmyEventBus.getDefault().unregister(this);
    }
}
