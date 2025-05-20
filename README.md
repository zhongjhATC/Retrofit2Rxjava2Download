这是一个由retrofit2和rxjava2一起搭配的网络框架

1.有些demo是用event来传递的，但我认为能尽量不用event的场合最好能不用（也不是说event不好，至于为什么不用event的原因可以搜索相关文章），所以这个demo是用handle来处理。

2.有进度的回调下载。

3.有销毁线程。

参考以下网站例子：

https://github.com/lizhangqu/CoreProgress

https://github.com/shuaijia/JsDownload

https://github.com/zhourongxin/RetrofitRxDownload

#### Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
  	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

  
#### Step 2. Add the dependency
 
```
dependencies {
	        implementation 'com.github.zhongjhATC:Retrofit2Rxjava2Download:1.0.6'
	}
```

例子

```
public class MainActivity extends AppCompatActivity implements DownloadListener {

    ```````
    ```````

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
            
    @Override
    protected void onDestroy() {
        FileDownloader.cancelDownload();
        super.onDestroy();
    }

}
```
