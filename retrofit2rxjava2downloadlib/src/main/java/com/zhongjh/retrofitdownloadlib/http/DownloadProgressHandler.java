package com.zhongjh.retrofitdownloadlib.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * 下载进度Handler
 *
 * @author zhongjh
 * @date 2025/5/20
 */
public abstract class DownloadProgressHandler implements DownloadCallBack {
    public static final int DOWNLOAD_SUCCESS = 0;
    public static final int DOWNLOAD_PROGRESS = 1;
    public static final int DOWNLOAD_FAIL = 2;

    protected ResponseHandler mHandler = new ResponseHandler(this, Looper.getMainLooper());

    /**
     * 发送消息，更新进度
     *
     * @param what         枚举
     * @param downloadInfo 下载文件信息
     */
    public void sendMessage(int what, DownloadInfo downloadInfo) {
        mHandler.obtainMessage(what, downloadInfo).sendToTarget();
    }


    /**
     * 处理消息
     *
     * @param message 消息
     */
    protected void handleMessage(Message message) {
        DownloadInfo progressBean = (DownloadInfo) message.obj;
        switch (message.what) {
            case DOWNLOAD_SUCCESS:
                // 下载成功
                onCompleted(progressBean.getFile());
                removeMessage();
                break;
            case DOWNLOAD_PROGRESS:
                // 下载中
                onProgress(progressBean);
                break;
            case DOWNLOAD_FAIL:
                // 下载失败
                onError(progressBean.getErrorMsg());
                break;
            default:
                removeMessage();
                break;
        }
    }

    private void removeMessage() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }


    protected static class ResponseHandler extends Handler {

        private final DownloadProgressHandler mProgressHandler;

        public ResponseHandler(DownloadProgressHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }
}
