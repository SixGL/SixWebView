# SixWebView
Webview以构造者设计模式封装，快捷使用方式
# 使用
  compile 'com.github.SixGL:SixWebView:V1.0'
## 使用方式
     SixWebView builder = new SixWebView.Builder(this).
                setWebView(mWebview)
                .setSupportJS(true)//设置是否支持jS交互
                .setUrl(url)
                .setJavaScriptEnabled("haha", new WebJs(this))// 第一个参数是js交互协议  第二个是js交互的对象
                .setWebChromeClient(new WebChromeClientListener() {
                    @Override
                    public void onReceivedTitle(android.webkit.WebView view, String title) {
                        Log.i("MainActivity->", "onReceivedTitle->" + title);
                    }
                })
                .setWebViewClient(new WebViewClientListener() {

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
                

### js交互对象
    public class WebJs extends JSInteraction {
		//客户端与js交互的方式  卸载这里
    public WebJs(Context context) {
        super(context);
    }

