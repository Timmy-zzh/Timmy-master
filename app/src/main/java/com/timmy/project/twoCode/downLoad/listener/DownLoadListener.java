package com.timmy.project.twoCode.downLoad.listener;

/**
 * Created by admin on 2017/5/26.
 * 下载回调
 */
public interface DownLoadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPause();

    void onCancel();
}
