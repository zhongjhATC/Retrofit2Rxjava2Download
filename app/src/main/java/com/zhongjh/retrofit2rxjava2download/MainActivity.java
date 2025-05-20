package com.zhongjh.retrofit2rxjava2download;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zhongjh.retrofitdownloadlib.http.DownloadInfo;
import com.zhongjh.retrofitdownloadlib.http.DownloadProgressHandler;
import com.zhongjh.retrofitdownloadlib.http.FileDownloader;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    /**
     * 权限申请自定义码
     */
    private final int GET_PERMISSION_REQUEST = 100;

    private TextView tv;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_test);
        url = "https://d.ifengimg.com/w1125_q90_webp/img1.ugc.ifeng.com/newugc/20190119/10/wemedia/ed80429fb44c8b298fb72aab635b689f2de75ce4_size2426_w3000_h2000.JPG";
    }

    public void download(View view) {
        boolean isOk = getPermissions();
        if (isOk) {
            FileDownloader.downloadFile(url, getApplication().getExternalCacheDir() + File.separator + "/apk/aa/bb",
                    "aaa.png", new DownloadProgressHandler() {

                        @Override
                        public void onProgress(DownloadInfo downloadInfo) {
                            int progress = downloadInfo.getProgress();
                            long fileSize = downloadInfo.getFileSize();
                            long speed = downloadInfo.getSpeed();
                            long usedTime = downloadInfo.getUsedTime();
                            tv.setText("下载中 : " + progress + "%" + " 文件大小:" + FileUtils.formatFileSize(fileSize) + " 平均速度：" + FileUtils.formatFileSize(speed) + "/s" + " 下载时间：" + FileUtils.formatTime(usedTime));
                        }

                        @Override
                        public void onCompleted(File file) {
                            Toast.makeText(MainActivity.this, "下载完成：" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MainActivity.this, "下载文件异常:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity", e.getMessage(), e);
                        }
                    });
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                // 读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;
                if (!writeGranted) {
                    size++;
                }
                if (size == 0) {
                    Toast.makeText(this, "你可以重新打开相关功能", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 获取权限
     */
    private boolean getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED) {
                return true;
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, GET_PERMISSION_REQUEST);
                return false;
            }
        } else {
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        FileDownloader.cancelDownload();
        super.onDestroy();
    }

}
