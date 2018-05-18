package com.zhongjh.retrofitdownloadlib.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 下载文件的url接口
 * Created by zhongjh on 2018/5/18.
 */
public interface DownloadService {
    /**
     * 下载文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载
}
