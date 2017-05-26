package com.timmy.project.twoCode.downLoad;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.timmy.R;
import com.timmy.library.util.Toast;


/**
 * 第二行代码实例:下载
 * 1.下载工具类设置:
 * 下载回调接口
 * 使用AsyncTask进行下载
 * 断点下载:获取本地sd卡已经下载好的文件大小
 * 使用OkHttp添加头部,设置下载进度
 * 获取网络下载的数据,和下载文件长度
 * 下载,暂停,取消
 * <p>
 * 2.服务: 通过bindService()绑定服务实现活动和服务的通信
 * 通过Notification通知实现下载进度展示,前台通知
 * 回调接口的实现
 * IBinder实现具体的下载逻辑
 * <p>
 * 3.在活动中:
 * 申请权限
 * 开启服务,绑定服务
 * 按钮操作
 */
public class TCDownLoadActivity extends AppCompatActivity implements View.OnClickListener {

    private DownLoadService.DownLoadBinder downLoadBinder;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downLoadBinder = (DownLoadService.DownLoadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcdown_load);

        Button start = (Button) findViewById(R.id.btn_start);
        Button pause = (Button) findViewById(R.id.btn_pause);
        Button cancel = (Button) findViewById(R.id.btn_cancel);

        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        cancel.setOnClickListener(this);

        Intent intent = new Intent(this, DownLoadService.class);
        startService(intent);
        bindService(intent, conn, BIND_AUTO_CREATE);

        //开启权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TCDownLoadActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.toastShort("拒绝权限将导致功能无法使用!");
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                downLoadBinder.startDownLoad(url);
                break;
            case R.id.btn_pause:
                downLoadBinder.pauseDownLoad();
                break;
            case R.id.btn_cancel:
                downLoadBinder.cancelDownLoad();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
