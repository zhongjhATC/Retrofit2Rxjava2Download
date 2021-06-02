package com.zhongjh.retrofitdownloadlib.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 下载的工具
 *
 * @author zhongjh
 * @date 2018/5/18
 */
public class DownloadHelper {

    /**
     * 超时15s
     */
    private static final int DEFAULT_TIMEOUT = 15;
    /**
     * 网络工具retrofit
     */
    private final Retrofit retrofit;
    /**
     * 下载进度、完成、失败等的回调事件
     */
    private final DownloadListener mDownloadListener;
    /**
     * 清除线程需要用到的
     */
    private Disposable disposable;

    /**
     * 构造函数初始化
     *
     * @param listener 回调函数
     */
    public DownloadHelper(DownloadListener listener) {
        this.mDownloadListener = listener;
        DownloadInterceptor mInterceptor = new DownloadInterceptor(listener);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baseurl.com/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 进行文件下载
     * @param url 网址
     * @param destDir 文件目录
     * @param fileName 文件名称
     */
    public void downloadFile(String url, final String destDir, final String fileName) {
        dispose();
        mDownloadListener.onStartDownload();
        retrofit.create(DownloadService.class)
                .download(url)
                // 请求网络 在调度者的io线程
                .subscribeOn(Schedulers.io())
                // 指定线程保存文件
                .observeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(@NonNull ResponseBody responseBody) throws Exception {
                        return saveFile(responseBody.byteStream(), destDir, fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseDownloadObserver<File>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    protected void onDownloadSuccess(File file) {
                        mDownloadListener.onFinishDownload(file);
                    }

                    @Override
                    protected void onDownloadError(Throwable e) {
                        mDownloadListener.onFail(e);
                    }
                });
    }

    /**
     * 销毁
     */
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 将文件写入本地
     *
     * @param destFileDir  目标文件夹
     * @param destFileName 目标文件名
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    private File saveFile(InputStream is, String destFileDir, String destFileName) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return file;

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
