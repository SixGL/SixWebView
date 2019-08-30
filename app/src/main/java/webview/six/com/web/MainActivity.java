package webview.six.com.web;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.six.webview.web.SixWebView;
import com.six.webview.web.listener.OnLongClickListener;
import com.six.webview.web.listener.WebChromeClientListener;
import com.six.webview.web.listener.WebViewClientListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static String url = "https://blog.csdn.net/qq_34501274";
    private android.webkit.WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.webview);
        AlertDialog dialog = new AlertDialog.Builder(this).
                setTitle("Title").
                setMessage("Message").
                setPositiveButton("positive", null).
                setNegativeButton("negative", null).
                create();
        dialog.show();
        mWebview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
//        AlertDialog
        url = "http://www.baidu.com";
        Map map=new HashMap<String,String>();
        map.put("gt", "token");
        SixWebView builder = new SixWebView.Builder(this).
                setWebView(mWebview)
                .setSupportJS(true)
                .setIsSupportAddPhoto(true)
                .setUrl(url)
                .setUserAgentString("UASSSS")
                .setWebHead(true,map)
                .setJavaScriptEnabled("haha", new WebJs(this))
                .setWebChromeClient(new WebChromeClientListener() {
                    @Override
                    public void onReceivedTitle(android.webkit.WebView view, String title) {
                        Log.i("MainActivity->", "onReceivedTitle->" + title);
                    }

                    @Override
                    public void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                        super.showFileChooserCallBack(filePathCallback, fileChooserParams);
                        Log.i("MainActivity->", "showFileChooserCallBack->");
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
    }


}
