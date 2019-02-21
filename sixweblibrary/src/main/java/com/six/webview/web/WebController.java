package com.six.webview.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.*;
import android.webkit.WebView;
import android.widget.Toast;

import com.six.webview.web.listener.OnLongClickListener;
import com.six.webview.web.listener.WebChromeClientListener;
import com.six.webview.web.listener.WebViewClientListener;

import java.util.Map;

/**
 * Created by admin on 2018/9/4.
 */

public class WebController {
    public String TAG = "SixWeb->C ";
    public Context mContext;
    public String url;
    public android.webkit.WebView webView;
    public boolean supportJs = true;
    public boolean isSupportWebHead = false;
    public Map<String, String> webHeadMap;
    public String jsName;
    public JSInteraction jsInteraction;
    public WebChromeClientListener webChromeClient;
    public WebViewClientListener webViewClient;

    public WebController(Context context) {
        mContext = context;
    }

    /**
     * 传入Webview
     */
    public void setWebView(android.webkit.WebView webView) {
        Log.i("Test->", "WebController->setWebView");
        this.webView = webView;
    }

    /**
     * 设置Webview 加载Url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置Js 交互协议
     *
     * @param jsName        js交互协议名字
     * @param jsInteraction js交互对象
     */
    public void setJavaScriptEnabled(String jsName, JSInteraction jsInteraction) {
        this.jsName = jsName;
        this.jsInteraction = jsInteraction;

    }

    /**
     * 设置webview支持添加请求头
     */
    public void setWebHead(boolean b, Map<String, String> headMap) {
        this.isSupportWebHead = b;
        this.webHeadMap = headMap;
    }

    /**
     * 设置是否支持js交互 默认支持js交互如果不需要支持 调用此方法，传入false
     */
    public void setSupportJS(boolean b) {
        this.supportJs = b;
    }

    /**
     * 设置WebChromeClient
     */
    public void setWebChromeClient(final WebChromeClientListener webChromeClient) {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (webChromeClient != null) {
                    webChromeClient.onReceivedTitle(view, title);
                } else {
                    Log.i(TAG, "onReceivedTitle-> WebChromeClientListener is null");
                }
            }
        });
    }

    /**
     * 设置WebViewClient
     */
    public void setWebViewClient(final WebViewClientListener webViewClient) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (webViewClient != null) {
                    webViewClient.shouldOverrideUrlLoading(view, url);
                } else {
                    Log.i(TAG, "onPageStarted-> webViewClientListener is null");
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (webViewClient != null) {
                    webViewClient.onPageStarted(view, url, favicon);
                } else {
                    Log.i(TAG, "onPageStarted-> webViewClientListener is null");
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webViewClient != null) {
                    webViewClient.onPageFinished(view, url);
                } else {
                    Log.i(TAG, "onPageFinished-> webViewClientListener is null");
                }
            }
        });
    }

    public void setOnLongClickListener(final OnLongClickListener onLongClickListener) {
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onLongClickListener != null) {
                    onLongClickListener.onLongClick(view);
                }
                return false;
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    public void setWebSetting() {
        WebSettings mWebSettings = webView.getSettings();
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setSavePassword(false);
        mWebSettings.setJavaScriptEnabled(supportJs);
        if (supportJs == true) {
            webView.addJavascriptInterface(jsInteraction, jsName);
        }
//            if (cahceEnabled == true) {
//                if (NetStatusUtil.isNetworkConnected(mCtonext)) {
//                    //根据cache-control获取数据。
//                    mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//                } else {
//                    // 没网，则从本地获取，即离线加载
//                    mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//                }
//            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 适配5.0不允许http和https混合使用情况
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebSettings.setTextZoom(100);
        mWebSettings.setDatabaseEnabled(true);
//            mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setLoadsImagesAutomatically(true);////支持自动加载图片
        mWebSettings.setSupportMultipleWindows(false);
        // 是否阻塞加载网络图片  协议http or https
        mWebSettings.setBlockNetworkImage(false);
        // 允许加载本地文件html  file协议
        mWebSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            mWebSettings.setAllowFileAccessFromFileURLs(false);
            // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            mWebSettings.setAllowUniversalAccessFromFileURLs(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        mWebSettings.setLoadWithOverviewMode(false);// 	是否使用概览模式
        mWebSettings.setUseWideViewPort(false);// 	是否允许使用 <viewport> 标签 将图片调整到适合webview的大小
        mWebSettings.setDomStorageEnabled(true);// 	是否使用文档存储
        mWebSettings.setNeedInitialFocus(true);// //当webview调用requestFocus时为webview设置节点
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebSettings.setGeolocationEnabled(true);// 是否使用地理位置
        // 缓存路径配置
//            String dir = mCtonext.getCacheDir().getAbsolutePath() + AGENTWEB_CACHE_PATCH;
        // 设置数据库路径  api19 已经废弃,这里只针对 webkit 起作用
//            mWebSettings.setGeolocationDatabasePath(dir);
//            mWebSettings.setDatabasePath(dir);
//            mWebSettings.setAppCachePath(dir);
        // 缓存文件最大值
        mWebSettings.setAppCacheMaxSize(Long.MAX_VALUE);
//            webView.setWebChromeClient(wvcc);
        if (isSupportWebHead == true && webHeadMap != null) {
            webView.loadUrl(url, webHeadMap);
        } else {
            if (isSupportWebHead == true) {
                Log.e(TAG, "设置了Webview请求头，但是请求头Map值为空,请检查setWebHead是否正确调用！");
            }
            webView.loadUrl(url);
        }
    }

    public static class WebParams {
        public String TAG = "SixWeb->P";
        public Context mContext;
        public String url;
        public android.webkit.WebView webView;
        public boolean supportJs;
        public boolean isSupportWebHead;
        public String jsName;
        public JSInteraction jsInteraction;
        //        public WebViewListener.WebChromeClientListener webChromeClient;
        public WebChromeClientListener webChromeClient;
        public WebViewClientListener webViewClient;
        public OnLongClickListener onLongClickListener;
        public Map<String, String> webHeadMap;
//        public WebViewListener.WebViewClientListener webViewClient;

        public WebParams(Context context) {
            mContext = context;
        }

        public void setWebSetting(WebController C) {
            C.setWebSetting();
        }

        /**
         * 组装Webview 所需控件
         *
         * @param C
         */
        public void applay(WebController C) {
            if (webView == null) {
                try {
                    throw new SixWebviewExcetion(TAG + "SixWebView is null");
                } catch (SixWebviewExcetion sixWebviewExcetion) {
                    sixWebviewExcetion.printStackTrace();
                }
            } else {
                C.setWebView(webView);
            }
            if (url != null && !TextUtils.isEmpty(url)) {
                C.setUrl(url);
            } else {
                Toast.makeText(mContext, "URL为空!", Toast.LENGTH_SHORT).show();
            }
            // 设置js交互
            if (supportJs == true) {
                if (jsName != null && jsInteraction != null) {
                    C.setJavaScriptEnabled(jsName, jsInteraction);
                } else {
                    try {
                        throw new SixWebviewExcetion(TAG + "Support JS Interaction ,But jsName or jsInteraction is  null");
                    } catch (SixWebviewExcetion sixWebviewExcetion) {
                        sixWebviewExcetion.printStackTrace();
                    }
                }
            } else {
                Log.i(TAG, "Don't Support JSInteraction");
            }

            if (webChromeClient != null) {
                C.setWebChromeClient(webChromeClient);
            } else {
                Log.i(TAG, "webChromeClientListener is null");
            }

            if (webViewClient != null) {
                C.setWebViewClient(webViewClient);
            } else {
                Log.i(TAG, "webViewClientListener is null");
            }
            if (onLongClickListener != null) {
                C.setOnLongClickListener(onLongClickListener);
            }
            C.setWebHead(isSupportWebHead, webHeadMap);

        }
    }
}
