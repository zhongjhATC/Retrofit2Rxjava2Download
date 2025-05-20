package com.zhongjh.retrofitdownloadlib.http;

import java.io.File;

/**
 * 下载回调
 *
 * @author zhongjh
 * @date 2025/5/20
 */
public interface DownloadCallBack {

    /**
     * 进度，运行在主线程
     *
     * @param downloadInfo 下载信息
     */
    void onProgress(DownloadInfo downloadInfo);

    /**
     * 运行在主线程
     *
     * @param file file
     */
    void onCompleted(File file);

    /**
     * 运行在主线程
     *
     * @param e 异常
     */
    void onError(Throwable e);

}
