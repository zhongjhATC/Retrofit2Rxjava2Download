package com.zhongjh.retrofitdownloadlib.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 下载文件的url接口
 *
 * @author zhongjh
 * @date 2018/5/18
 */
public interface DownloadService {

    /**
     * 直接使用网址下载文件
     * @param url 网址
     * @return Observable
     */
    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);
}
