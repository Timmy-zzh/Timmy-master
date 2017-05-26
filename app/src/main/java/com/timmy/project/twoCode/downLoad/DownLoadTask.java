package com.timmy.project.twoCode.downLoad;

import android.os.AsyncTask;
import android.os.Environment;

import com.timmy.library.util.Logger;
import com.timmy.project.twoCode.downLoad.listener.DownLoadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/5/26.
 * 第一个参数保存的是下载的url
 * 文件保存在sd卡中
 * 1.创建文件
 * 2.获取下载文件的长度
 * 3.发起网络访问进行下载
 */
public class DownLoadTask extends AsyncTask<String, Integer, Integer> {

    private String TAG = "DownLoadTask";
    public static final int TYPE_FAILE = 0;
    public static final int TYPE_SUCCESS = 1;
    public static final int TYPE_CANCEL = 2;
    public static final int TYPE_PAUSE = 3;


    private DownLoadListener mListener;
    private long downLoadLength;//已经下载文件的长度
    private boolean isCancel;
    private boolean isPause;
    private int lastProgress;//最后下载的进度

    public DownLoadTask(DownLoadListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Logger.d(TAG, "onPreExecute");
    }

    @Override
    protected Integer doInBackground(String... params) {
        Logger.d(TAG, "doInBackground");
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;

        //下载的url
        String url = params[0];
        //下载文件名称
        String fileName = url.substring(url.lastIndexOf("/"));
        //文件保存的路径
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + fileName;
        Logger.d(TAG, "filePath:" + filePath);
        File downFile = new File(filePath);
        if (downFile.exists()) {
            downLoadLength = downFile.length();
        }
        //获取需要下载文件的长度
        long contentLength = getContentLength(url);
        Logger.d(TAG, "contentLength:" + contentLength + ",downLoadLength:" + downLoadLength);
        //判断获取到的下载文件的长度->返回下载状态到主线程进行处理
        if (contentLength == 0) {
            return TYPE_FAILE;
        } else if (contentLength == downLoadLength) {
            //说明下载成功
            return TYPE_SUCCESS;
        }

        //未下载,或者下载未完成
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                //添加头部,指定从那个位置开始下载
                .addHeader("RANGE", "bytes=" + downLoadLength + "-")
                .url(url)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                inputStream = response.body().byteStream();
                //IO流获取网络数据
                randomAccessFile = new RandomAccessFile(downFile, "rw");
                randomAccessFile.seek(downLoadLength);

                byte[] bytes = new byte[1024];
                int total = 0;
                int len;
                while ((len = inputStream.read(bytes)) != -1) {//从输入流获取数据
                    if (isCancel) {
                        //暂停
                        return TYPE_CANCEL;
                    } else if (isPause) {
                        return TYPE_PAUSE;
                    } else {
                        total += len;
                        Logger.d(TAG, "total:" + total + ",len:" + len);
                        //往文件中写数据
                        randomAccessFile.write(bytes, 0, len);
                        int progress = (int) ((total + downLoadLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                if (inputStream != null) {
                    inputStream.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                //取消下载,需要将文件删除
                if (isCancel && downFile != null) {
                    downFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILE;
    }

    /**
     * 获取需要下载文件的长度
     *
     * @param url
     * @return
     */
    private long getContentLength(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Logger.d(TAG, "onProgressUpdate progerss:" + values[0]);
        int progress = values[0];
        if (mListener != null && progress > lastProgress) {
            mListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Logger.d(TAG, "onPostExecute: result:" + integer);
        int result = integer;
        switch (integer) {
            case TYPE_SUCCESS:
                mListener.onSuccess();
                break;
            case TYPE_FAILE:
                mListener.onFailed();
                break;
            case TYPE_PAUSE:
                mListener.onPause();
                break;
            case TYPE_CANCEL:
                mListener.onCancel();
                break;
        }
    }

    public void pauseDownload() {
        isPause = true;
    }

    public void cancelDownload() {
        isCancel = true;
    }
}
