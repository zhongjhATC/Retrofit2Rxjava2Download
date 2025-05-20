package com.zhongjh.retrofitdownloadlib.http;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 文件下载器
 *
 * @author zhongjh
 * @date 2025/5/20
 */
public class FileDownloader {
    static CompositeDisposable mDisposable = new CompositeDisposable();

    /**
     * 下载文件法2(使用RXJava更新UI)
     *
     * @param url             下载地址
     * @param destDir         下载目录
     * @param fileName        文件名
     * @param progressHandler 进度handler
     */
    public static void downloadFile(final String url, final String destDir, final String fileName, final DownloadProgressHandler progressHandler) {
        final DownloadInfo downloadInfo = new DownloadInfo();
        DownloadApi apiService = RetrofitHelper.getInstance().getApiService(DownloadApi.class);
        Observable<ResponseBody> responseBodyObservable = apiService.downLoad(url);
        responseBodyObservable
                .flatMap((Function<ResponseBody, ObservableSource<DownloadInfo>>) responseBody -> Observable.create(emitter -> {
                    InputStream inputStream = null;
                    long total = 0;
                    long responseLength;
                    FileOutputStream fos = null;
                    try {
                        byte[] buf = new byte[1024 * 8];
                        int len;
                        responseLength = responseBody.contentLength();
                        inputStream = responseBody.byteStream();

                        File dir = new File(destDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        final File file = new File(destDir, fileName);
                        file.createNewFile();
                        downloadInfo.setFile(file);
                        downloadInfo.setFileSize(responseLength);

                        fos = new FileOutputStream(file);
                        int progress;
                        int lastProgress = -1;
                        long startTime = System.currentTimeMillis();
                        // 开始下载时获取开始时间
                        while ((len = inputStream.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            total += len;
                            progress = (int) (total * 100 / responseLength);
                            long curTime = System.currentTimeMillis();
                            long usedTime = (curTime - startTime) / 1000;
                            if (usedTime == 0) {
                                usedTime = 1;
                            }
                            // 平均每秒下载速度
                            long speed = (total / usedTime);
                            // 如果进度与之前进度相等，则不更新，如果更新太频繁，则会造成界面卡顿
                            if (progress != lastProgress) {
                                downloadInfo.setSpeed(speed);
                                downloadInfo.setProgress(progress);
                                downloadInfo.setCurrentSize(total);
                                downloadInfo.setUsedTime(usedTime);
                                if (!emitter.isDisposed()) {
                                    emitter.onNext(downloadInfo);
                                }
                            }
                            lastProgress = progress;
                        }
                        fos.flush();
                        downloadInfo.setFile(file);
                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    } catch (Exception e) {
                        downloadInfo.setErrorMsg(e);
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    } finally {
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(DownloadInfo downloadInfo) {
                        progressHandler.onProgress(downloadInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressHandler.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        progressHandler.onCompleted(downloadInfo.getFile());
                    }
                });
    }

    public static void cancelDownload() {
        mDisposable.clear();
    }

}
