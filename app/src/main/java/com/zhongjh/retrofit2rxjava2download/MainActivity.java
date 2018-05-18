package com.zhongjh.retrofit2rxjava2download;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhongjh.retrofitdownloadlib.http.DownloadHelper;
import com.zhongjh.retrofitdownloadlib.http.DownloadListener;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DownloadListener {

    private TextView tv;
    private String url;

    // 初始化
    private DownloadHelper mDownloadHelper = new DownloadHelper("http://www.baseurl.com", this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_test);
        url = "http://assets.geilicdn.com/channelapk/1000n_shurufa_1.9.6.apk";
    }

    public void download(View view) {
        // 调用方法
        mDownloadHelper.downloadFile(url, Environment.getExternalStorageDirectory() + File.separator + "/apk", "aaa.xlsx");
    }

    @Override
    protected void onDestroy() {
        // 销毁
        mDownloadHelper.dispose();
        super.onDestroy();
    }

    /**
     * 加载前
     */
    @Override
    public void onStartDownload() {

    }

    /**
     * 加载中
     */
    @Override
    public void onProgress(int progress) {
        tv.setText("下载中 : " + progress + "%");
    }

    /**
     * 加载后
     *
     * @param file 文件
     */
    @Override
    public void onFinishDownload(File file) {
        tv.setText("下载成功。\n" + file.getAbsolutePath());
    }

    /**
     * 加载失败
     *
     * @param ex 异常
     */
    @Override
    public void onFail(Throwable ex) {
        tv.setText("下载失败 : " + ex.getLocalizedMessage());
    }

}
