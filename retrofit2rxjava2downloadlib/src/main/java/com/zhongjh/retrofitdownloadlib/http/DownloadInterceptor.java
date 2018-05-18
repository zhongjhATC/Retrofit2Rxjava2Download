package com.zhongjh.retrofitdownloadlib.http;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 处理数据
 * Created by zhongjh on 2018/5/18.
 */
public class DownloadInterceptor implements Interceptor {

    private DownloadListener downloadListener;

    DownloadInterceptor(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new DownloadResponseBody(response.body(), downloadListener))
                .build();
    }
}
