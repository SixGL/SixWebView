# SixWebView
Webview以构造者设计模式封装，快捷使用方式

     
## 依赖
    项目gradle
    allprojects {
    maven { url 'https://jitpack.io' }
    }
    

    app.gradle 依赖
    
      implementation 'com.github.SixGL:SixWebView:v1.2.3'
## 使用方式
    
### webview使用
        Map map = new HashMap<String, String>();
        map.put("gt", "token");
        SixWebView builder = new SixWebView.Builder(this).
                setWebView(mWebview)
                .setSupportJS(true)// 支持js交互
                .setIsSupportAddPhoto(true)// 支持webview添加图片
                .setUrl(url)
                .isWebScreenHot(true)// 支持截取长图
                .setUserAgentString("UA_SWeb")// 设置ua
                .setWebHead(true, map)// 设置webview的请求头
                .setJavaScriptEnabled("haha", new WebJs(this))// 交互协议、交互统一管理类
                .setWebChromeClient(new WebChromeClientListener() {
                    @Override
                    public void onReceivedTitle(android.webkit.WebView view, String title) {
                        Log.i(tag, "onReceivedTitle " + title);
                    }

                    @Override
                    public void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                        super.showFileChooserCallBack(filePathCallback, fileChooserParams);
                        Log.i(tag, "showFileChooserCallBack");
                        /**
                         * 选择相册还是相机的弹窗由客户端自己定义
                         * 申请权限等操作由客户端进行环境
                         * */

                    }
                })
                .setWebViewClient(new WebViewClientListener() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return super.shouldOverrideUrlLoading(view, url);

                    }

                    @Override
                    public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                        Log.i(tag, "onPageStarted = " + url);
                    }

                    @Override
                    public void onPageFinished(android.webkit.WebView view, String url) {
                        Log.i(tag, "onPageFinishe = " + url);
                    }
                })
                .setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public void onLongClick(View view) {
                        super.onLongClick(view);
                        Log.i(tag, "onLongClick ");
                    }
                })
                .loadUrl();

### webview 截取长图

      /**
         * 1.如果截取不成功，请在 SixWebView.Builder中设置： isWebScreenHot(true)
         * 2.如果只使用webview截长图功能 :请在webview初始化前设置以下代码
         *  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         *                 android.webkit.WebView.enableSlowWholeDocumentDraw();
         *             }
         *             webView.setDrawingCacheEnabled(true);
         * */
        // 具体请看源码里的注释
        instance = SWebScreenShot.INSTANCE
                .toInit(MainActivity.this, mWebview)
                .isScreenSHotLoading(true) // 默认true,提供截图时的loading
                .setDelayTime(200)// 截取长图延迟时间 （需要延迟原因：请看源码注释）
                .isReloadWeb(true)// webview重载
                .toScreenSHot()// webview 截取前准备（测量宽高）
                .toCancvas(new ScreenShotCallback() {// 截取回调
                    @Override
                    public void screenShotResult(String path) {
                        Log.e("SixWeb", "screenShotResult " + path);
                    }

                    @Override
                    public void screenShotBefore() {
                        super.screenShotBefore();
                    // 使用自定义loading，需要把设置：isScreenSHotLoading(false)
                    // 截取开始前的回调  开发者可自定义展示loading
                        Log.e("SixWeb", "screenShotBefore ");
                    }

                    @Override
                    public void screenShotafter() {
                        super.screenShotafter();
                  // 截取结束后的回调  开发者关闭自定义loading
                        Log.e("SixWeb", "screenShotafter ");

                    }
                });

 #### 释放截取webview产生的bitmap：

     注意：一定在当前界面销毁时调用。
     @Override
       protected void onDestroy() {
           super.onDestroy();
             if (instance != null) {
                instance.recycle();
          }
      }

### 使用截图功能，需要申请存储权限

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

## js交互对象
    public class WebJs extends JSInteraction {
		//客户端与js交互的方式  卸载这里
    public WebJs(Context context) {
        super(context);
    }
