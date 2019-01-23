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
	        implementation 'com.github.zhongjhATC:Retrofit2Rxjava2Download:1.0.1'
	}
```

例子

```
public class MainActivity extends AppCompatActivity implements DownloadListener {

    ```````
    ```````

    // 初始化
    private DownloadHelper mDownloadHelper = new DownloadHelper("http://www.baseurl.com", this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ```````
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
```
