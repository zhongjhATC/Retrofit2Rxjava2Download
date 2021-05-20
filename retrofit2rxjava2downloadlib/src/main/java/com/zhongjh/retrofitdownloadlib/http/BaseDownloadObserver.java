package com.zhongjh.retrofitdownloadlib.http;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 用于只暴露success和error
 *
 * @author zhongjh
 * @date 2018/5/18
 * @param <T>
 */
public abstract class BaseDownloadObserver<T> implements Observer<T> {

    /**
     * 开始网络操作前的步骤
     * @param disposable 用于释放
     */
    @Override
    public abstract void onSubscribe(@NonNull Disposable disposable);

    @Override
    public void onNext(@NonNull T t) {
        onDownloadSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onDownloadError(e);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 下载成功
     * @param t 实体
     */
    protected abstract void onDownloadSuccess(T t);

    /**
     * 下载异常
     * @param e 实体
     */
    protected abstract void onDownloadError(Throwable e);
}
