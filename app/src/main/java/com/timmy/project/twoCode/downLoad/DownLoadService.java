package com.timmy.project.twoCode.downLoad;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import com.timmy.R;
import com.timmy.library.util.Logger;
import com.timmy.library.util.Toast;
import com.timmy.project.twoCode.downLoad.listener.DownLoadListener;

import java.io.File;

/**
 * 服务:开启服务进行下载
 * 在服务中开启通知
 */
public class DownLoadService extends Service {

    private String TAG = "DownLoadService";
    private DownLoadTask loadTask;
    private String downloadUrl;

    public DownLoadService() {
    }

    private DownLoadBinder binder = new DownLoadBinder();

    class DownLoadBinder extends Binder {

        /**
         * 开始下载
         */
        public void startDownLoad(String url) {
            Logger.d(TAG, "startDownLoad");
            downloadUrl = url;
            if (loadTask == null) {
                loadTask = new DownLoadTask(mListener);
                loadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                Toast.toastShort("Downloading...");
            }
        }

        //暂停下载
        public void pauseDownLoad() {
            Logger.d(TAG, "pauseDownLoad");
            if (loadTask != null) {
                loadTask.pauseDownload();
//                loadTask.cancel(true);
            }
        }

        /**
         * 取消下载
         * 取消通知
         */
        public void cancelDownLoad() {
            Logger.d(TAG, "cancelDownLoad");
            if (loadTask != null) {
                loadTask.cancelDownload();
//                loadTask.cancel(true);
            } else {
                if (downloadUrl != null) {
                    //删除之前的文件
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String director = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    Logger.d(TAG, "director:" + director);
                    File downFile = new File(director + fileName);
                    if (downFile.exists()) {
                        downFile.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    downloadUrl = null;
                    Toast.toastShort("Cancel");
                }
            }
        }
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String msg, int progress) {
        Intent intent = new Intent(this, TCDownLoadActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(msg)
                .setContentIntent(pi);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    DownLoadListener mListener = new DownLoadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            //下载成功
            loadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.toastShort("下载成功");
        }

        @Override
        public void onFailed() {
            //下载失败
            loadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Faile", -1));
            Toast.toastShort("下载失败");
        }

        @Override
        public void onPause() {
            loadTask = null;
            Toast.toastShort("暂停下载");
        }

        @Override
        public void onCancel() {
            loadTask = null;
            stopForeground(true);
            Toast.toastShort("取消下载");
        }
    };

}
