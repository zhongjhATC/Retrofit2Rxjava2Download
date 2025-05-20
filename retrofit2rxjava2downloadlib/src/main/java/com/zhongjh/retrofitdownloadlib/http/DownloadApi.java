package com.zhongjh.retrofitdownloadlib.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * 下载Api
 *
 * @author zhongjh
 * @date 2025/5/20
 */
public interface DownloadApi {

    /**
     * 下载文件
     *
     * @return ResponseBody
     */
    @Streaming
    @GET
    Observable<ResponseBody> downLoad(@Url String url);


    /**
     * 下载文件
     *
     * @param range Range表示断点续传的请求头参数
     * @param url   下载url
     * @return ResponseBody
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("Range") String range, @Url String url);
}
