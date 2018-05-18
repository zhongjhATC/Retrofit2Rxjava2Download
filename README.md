#### Step 1. Add it in your root build.gradle at the end of repositories:

```
这是一个由retrofit2和rxjava2一起搭配的网络框架
参考以下网站例子：
https://github.com/lizhangqu/CoreProgress
https://github.com/shuaijia/JsDownload
https://github.com/zhourongxin/RetrofitRxDownload


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
	        compile 'com.github.Chenayi:RetrofitRxDownload:1.0.2'
	}
```
