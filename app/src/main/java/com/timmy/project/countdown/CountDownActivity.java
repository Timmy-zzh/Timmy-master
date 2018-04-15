package com.timmy.project.countdown;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.timmy.R;

import org.reactivestreams.Subscriber;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 倒计时实现方案
 */
public class CountDownActivity extends AppCompatActivity {

    private final static int DEFAULT_COUNT = 10;
    private TextView tvTime;
    private static int whatNum = 100;
    private int count;

    //注意内存泄漏
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (count > 0) {
                        count--;
                        tvTime.setText(count + "");
                        handler.sendEmptyMessageDelayed(whatNum, 1000);
                    } else {
                        tvTime.setText("倒计时结束");
                        handler.removeCallbacks(runnable);
                    }
                    break;
                case 101:
                    if (count > 0) {
                        count--;
                        tvTime.setText(count + "");
                    } else {
                        tvTime.setText("倒计时结束");
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                    }
                    break;
                case 102:
                    if (count > 0) {
                        count--;
                        tvTime.setText(count + "");
                    } else {
                        tvTime.setText("倒计时结束");
                        if (scheduled != null) {
                            scheduled.shutdown();
                            scheduled = null;
                        }
                    }
                    break;
            }
        }
    };
    private Timer timer;
    private ScheduledExecutorService scheduled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        tvTime = findViewById(R.id.tv_time);
    }

    public void onViewClicked(View view) {
        count = DEFAULT_COUNT;
        tvTime.setText(count + "");
        switch (view.getId()) {
            case R.id.btn_one:
                handlerPostDelayed();
                break;
            case R.id.btn_two:
                timerRun();
                break;
            case R.id.btn_three:
                scheduled();
                break;
            case R.id.btn_four:
                rxJava();
                break;
            case R.id.btn_five:
                countDownTimer();
                break;
        }
    }

    private void countDownTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String value = String.valueOf((int) (millisUntilFinished / 1000));
                tvTime.setText(value);
            }

            @Override
            public void onFinish() {
                tvTime.setText("倒计时结束");
            }
        };
        countDownTimer.start();
    }

    private void rxJava() {
        final long count = DEFAULT_COUNT;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take((int) (count + 1)) //设置总共发送的次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;//long 值是从小到大，倒计时需要将值倒置
                    }
                })
                .subscribeOn(Schedulers.computation())
                // doOnSubscribe 执行线程由下游逻辑最近的 subscribeOn() 控制，下游没有 subscribeOn() 则跟Subscriber 在同一线程执行
                //执行计时任务前先将 button 设置为不可点击
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        tvTime.setEnabled(false);//在发送数据的时候设置为不能点击
                        tvTime.setBackgroundColor(Color.GRAY);//背景色设为灰色
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) { //接收到一条就是会操作一次UI
                        String value = String.valueOf(aLong);
                        tvTime.setText(value);
                    }

                    @Override
                    public void onComplete() {
                        tvTime.setText("倒计时结束");
                        tvTime.setEnabled(true);
                        tvTime.setBackgroundColor(Color.parseColor("#f97e7e"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void scheduled() {
        scheduled = new ScheduledThreadPoolExecutor(1);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(102, 1000);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void timerRun() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(101, 1000);
            }
        };
        //每隔1000毫秒执行一次
        timer.schedule(timerTask, 0, 1000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(100);
        }
    };

    private void handlerPostDelayed() {
        handler.postDelayed(runnable, 1000);
    }
}
