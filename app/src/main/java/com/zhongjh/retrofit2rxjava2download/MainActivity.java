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
    private DownloadHelper mDownloadHelper = new DownloadHelper("http://www.baseurl.com", this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_test);
        url = "http://assets.geilicdn.com/channelapk/1000n_shurufa_1.9.6.apk";
    }

    public void download(View view) {
        mDownloadHelper
                .downloadFile(url, Environment.getExternalStorageDirectory() + File.separator + "/apk", "aaa.xlsx");
    }

    @Override
    protected void onDestroy() {
        mDownloadHelper.dispose();
        super.onDestroy();
    }

    @Override
    public void onStartDownload() {

    }

    @Override
    public void onProgress(int progress) {
        tv.setText("下载中 : " + progress + "%");
    }

    @Override
    public void onFinishDownload(File file) {
        tv.setText("下载成功。\n" + file.getAbsolutePath());
    }

    @Override
    public void onFail(Throwable ex) {
        tv.setText("下载失败 : " + ex.getLocalizedMessage());
    }
}
