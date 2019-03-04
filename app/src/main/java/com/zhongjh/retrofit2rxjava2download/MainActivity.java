package com.zhongjh.retrofit2rxjava2download;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongjh.retrofitdownloadlib.http.DownloadHelper;
import com.zhongjh.retrofitdownloadlib.http.DownloadListener;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DownloadListener {

    private final int GET_PERMISSION_REQUEST = 100; //权限申请自定义码

    private TextView tv;
    private String url;

    // 初始化
    private DownloadHelper mDownloadHelper = new DownloadHelper("http://www.baseurl.com", this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_test);
        url = "https://www.baidu.com/img/bd_logo1.png";
    }

    public void download(View view) {
        boolean isOk = getPermissions();
        if (isOk)
            // 调用方法
            mDownloadHelper.downloadFile(url, Environment.getExternalStorageDirectory() + File.separator + "/apk/aa/bb", "aaa.png");
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
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
