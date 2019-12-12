package webview.six.com.web;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.six.webview.web.screenshot.SWebScreenShot;
import com.six.webview.web.screenshot.ScreenShotCallback;

import java.util.HashMap;
import java.util.Map;

import tsou.cn.lib_primissions.HxgPermissionFail;
import tsou.cn.lib_primissions.HxgPermissionHelper;
import tsou.cn.lib_primissions.HxgPermissionSuccess;

public class MainActivity extends AppCompatActivity {

    private static String url = "https://blog.csdn.net/qq_34501274";
    private static String tag = "SWeb -> ";
    private android.webkit.WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.webview);
        findViewById(R.id.btnWeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,PreviewActivity.class));
                HxgPermissionHelper.with(MainActivity.this)
                        .requestCode(100)
                        .requestPermission(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE

                        )
                        .request();
            }
        });
        mWebview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        url = "https://blog.csdn.net/qq_34501274";
        Map map = new HashMap<String, String>();
        map.put("gt", "token");
        SixWebView builder = new SixWebView.Builder(this).
                setWebView(mWebview)
                .setSupportJS(true)// 支持js交互
                .setIsSupportAddPhoto(true)// 支持webview添加图片
                .setUrl(url)
                .isWebScreenHot(true)
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
    }

    SWebScreenShot instance;

    @HxgPermissionSuccess(requestCode = 100)
    private void success() {
        /**
         * 1.如果截取不成功，请在 SixWebView.Builder中设置： isWebScreenHot(true)
         * 2.如果使用自己的webview:请在webview初始化前设置以下代码
         *  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         *                 android.webkit.WebView.enableSlowWholeDocumentDraw();
         *             }
         *             webView.setDrawingCacheEnabled(true);
         * */
        // 具体请看源码里的注释
        instance = SWebScreenShot.INSTANCE
                .toInit(MainActivity.this, mWebview)
                .isScreenSHotLoading(true)
                .setDelayTime(300)
                .isReloadWeb(true)

                .toScreenSHot()
                .toCancvas(new ScreenShotCallback() {
                    @Override
                    public void screenShotResult(String path) {
                        Log.e("SixWeb", "screenShotResult " + path);
                        Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
                        intent.putExtra("url", path);
                        startActivity(intent);
                    }

                    @Override
                    public void screenShotBefore() {
                        super.screenShotBefore();
                        Log.e("SixWeb", "screenShotBefore ");
                    }

                    @Override
                    public void screenShotafter() {
                        super.screenShotafter();
                        Log.e("SixWeb", "screenShotafter ");

                    }
                });
    }

    @HxgPermissionFail(requestCode = 100)
    private void fail() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HxgPermissionHelper.requestPermissionsResult(this, requestCode, permissions);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (instance != null) {
            instance.recycle();
        }
    }
}
