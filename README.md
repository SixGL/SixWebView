# SixWebView
Webview以构造者设计模式封装，快捷使用方式

     
## 依赖
    项目gradle
    allprojects {
    maven { url 'https://jitpack.io' }
    }
    

    app.gradle 依赖
    
     implementation 'com.github.SixGL:SixWebView:V1.2.1'
## 使用方式
    
    

     SixWebView builder = new SixWebView.Builder(this).
                setWebView(mWebview)
                .setSupportJS(true)
		.setIsSupportAddPhoto(true)
                .setUrl(url)
                .setWebHead(true,null)// 设置为true 第二个参数是一个Map<String ,String >
                .setJavaScriptEnabled("haha", new WebJs(this))
                .setWebChromeClient(new WebChromeClientListener() {
                    @Override
                    public void onReceivedTitle(android.webkit.WebView view, String title) {
                        Log.i("MainActivity->", "onReceivedTitle->" + title);
                    }
		     @Override
                   public void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback,WebChromeClient.FileChooserParams fileChooserParams) {
                        super.showFileChooserCallBack(filePathCallback, fileChooserParams);
                        Log.i("MainActivity->", "showFileChooserCallBack->");
                            /**
                             * 选择相册还是相机的弹窗由客户端自己定义
                             * 申请权限等操作由客户端进行操作
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
                        Log.i("MainActivity->", "onPageStarted->" + url);
                    }

                    @Override
                    public void onPageFinished(android.webkit.WebView view, String url) {
                        Log.i("MainActivity->", "onPageFinished-> webViewClientListener ->" + url);
                    }
                })
                .setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public void onLongClick(View view) {
                        super.onLongClick(view);
                        Log.i("MainActivity->", "onLongClick-> ");
                    }
                })
                .loadUrl();

## js交互对象
    public class WebJs extends JSInteraction {
		//客户端与js交互的方式  卸载这里
    public WebJs(Context context) {
        super(context);
    }

