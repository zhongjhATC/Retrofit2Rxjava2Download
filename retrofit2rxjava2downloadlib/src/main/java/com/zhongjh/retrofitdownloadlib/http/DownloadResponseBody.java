package com.zhongjh.retrofitdownloadlib.http;


import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


/**
 * 处理返回数据
 *
 * @author zhongjh
 * @date 2018/5/18
 */
public class DownloadResponseBody extends ResponseBody {

    private final ResponseBody responseBody;

    private final DownloadListener mDownloadListener;
    private final DownloadHandler mDownloadHandler = new DownloadHandler();

    /**
     * BufferedSource 是okio库中的输入流，这里就当作inputStream来使用。
     */
    private BufferedSource bufferedSource;


    DownloadResponseBody(ResponseBody responseBody, DownloadListener mDownloadListener) {
        this.responseBody = responseBody;
        this.mDownloadListener = mDownloadListener;
        this.mDownloadHandler.initHandler(mDownloadListener);
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 处理数据
     * @param source 数据源
     * @return 返回处理后的数据源
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                Log.e("download", "read: "+ (int) (totalBytesRead * 100 / responseBody.contentLength()));
                if (null != mDownloadListener) {
                    if (bytesRead != -1) {
                        // 回调进度ui
                        mDownloadHandler.onProgress((int) (totalBytesRead * 100 / responseBody.contentLength()));
                    }

                }
                return bytesRead;
            }
        };

    }





}
