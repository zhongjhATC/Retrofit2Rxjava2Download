package com.zhongjh.retrofitdownloadlib.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit帮助类
 *
 * @author zhongjh
 * @date 2025/5/20
 */
public class RetrofitHelper {
    private volatile static RetrofitHelper sInstance;
    private final Retrofit mRetrofit;

    private RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://zhongjh.github.cn.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitHelper getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitHelper.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 返回接口服务实例
     */
    public <T> T getApiService(Class<T> clz) {
        return mRetrofit.create(clz);
    }
}
