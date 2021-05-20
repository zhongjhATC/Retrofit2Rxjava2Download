package com.zhongjh.retrofitdownloadlib.http;

import java.io.File;

/**
 * 下载进度回调
 *
 * @author zhongjh
 * @date 2018/5/18
 */
public interface DownloadListener {

    /**
     * 开始下载
     */
    void onStartDownload();

    /**
     * 下载进度
     * @param progress 进度数值，1-100
     */
    void onProgress(int progress);

    /**
     * 下载完成
     * @param file 文件
     */
    void onFinishDownload(File file);

    /**
     * 下载失败
     * @param ex 异常信息
     */
    void onFail(Throwable ex);

}
