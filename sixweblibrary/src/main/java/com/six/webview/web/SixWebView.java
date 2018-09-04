package com.six.webview.web;

import android.content.Context;
import android.util.Log;

import com.six.webview.web.listener.OnLongClickListener;
import com.six.webview.web.listener.WebChromeClientListener;
import com.six.webview.web.listener.WebViewClientListener;

/**
 * Created by admin on 2018/9/4.
 */

public class SixWebView {

    private Context mContext;
    private final WebController C;

    public SixWebView(Context context) {
        this.mContext = context;
        C = new WebController(context);
    }

    public void setWebView(android.webkit.WebView webView) {
        Log.i("Test->","SixWebView->setWebView");
        C.setWebView(webView);
    }


    public void setUrl(String url) {
        C.setUrl(url);
    }


    public void setJavaScriptEnabled(String jsName, JSInteraction jsInteraction) {
        C.setJavaScriptEnabled(jsName, jsInteraction);
    }


    public void setSupportJS(boolean b) {
        C.setSupportJS(b);
    }

    public static class Builder {
        private final WebController.WebParams P;

        public Builder(Context context) {
            P = new WebController.WebParams(context);
        }

        /**
         * 传入Webview
         */
        public Builder setWebView(android.webkit.WebView webView) {
            Log.i("Test->","Builder->setWebView");
            P.webView = webView;
            return this;
        }

        /**
         * 设置Webview 加载Url
         */
        public Builder setUrl(String url) {
            P.url = url;
            return this;
        }

        /**
         * 设置Js 交互协议
         *
         * @param jsName        js交互协议名字
         * @param jsInteraction js交互对象
         */
        public Builder setJavaScriptEnabled(String jsName, JSInteraction jsInteraction) {
            P.jsName = jsName;
            P.jsInteraction = jsInteraction;
            return this;
        }

        /**
         * 设置是否支持js交互
         * 默认支持js交互如果不需要支持 调用此方法，传入false
         */
        public Builder setSupportJS(boolean b) {
            P.supportJs = b;
            return this;
        }

        /**
         * 设置WebChromeClient
         */
        public Builder setWebChromeClient(WebChromeClientListener webChromeClient) {
            P.webChromeClient = webChromeClient;
            return this;
        }

        /**
         * 设置WebViewClient
         */
        public Builder setWebViewClient(WebViewClientListener webViewClient) {
            P.webViewClient = webViewClient;
            return this;
        }
        /**
         * 设置WWebview 长按事件
         */
        public Builder setOnLongClickListener(OnLongClickListener onLongClickListener) {
            P.onLongClickListener = onLongClickListener;
            return this;
        }



        private SixWebView create() {
            SixWebView webView = new SixWebView(P.mContext);
            P.applay(webView.C);
            return webView;
        }

        public SixWebView loadUrl() {
            SixWebView webView = create();
            P.setWebSetting();
            return webView;
        }
    }


}
