package com.six.webview.web;

import android.content.Context;
import android.util.Log;

import com.six.webview.web.listener.OnLongClickListener;
import com.six.webview.web.listener.WebChromeClientListener;
import com.six.webview.web.listener.WebViewClientListener;

import java.util.Map;

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
         * @param webView 传入Webview对象
         */
        public Builder setWebView(android.webkit.WebView webView) {
            Log.i("Test->","Builder->setWebView");
            P.webView = webView;
            return this;
        }

        /**
         * @param url 加载Url
         */
        public Builder setUrl(String url) {
            P.url = url;
            return this;
        }

        /**
         * @param jsName        js交互协议名字
         * @param jsInteraction js交互对象
         */
        public Builder setJavaScriptEnabled(String jsName, JSInteraction jsInteraction) {
            P.jsName = jsName;
            P.jsInteraction = jsInteraction;
            return this;
        }

        /**
         * @param b 设置是否支持js交互
         * 默认支持js交互如果不需要支持 调用此方法，传入false
         */
        public Builder setSupportJS(boolean b) {
            P.supportJs = b;
            return this;
        }
        /**
         * @param screenHot true 支持webview截长图
         */
        public Builder isWebScreenHot(boolean screenHot) {
            P.supportWebscreenHot = screenHot;
            return this;
        }
        /**
         * @param b true 支持添加请求头
         * @param headMap 请求头的传输格式
         * */
        public Builder setWebHead(boolean b,Map<String, String> headMap) {
            P.isSupportWebHead = b;
            P.webHeadMap = headMap;
            return this;
        }

        /**
         * @param webChromeClient
         * 设置WebChromeClient
         */
        public Builder setWebChromeClient(WebChromeClientListener webChromeClient) {
            P.webChromeClient = webChromeClient;
            return this;
        }

        /**
         * @param webViewClient
         * 设置WebViewClient
         */
        public Builder setWebViewClient(WebViewClientListener webViewClient) {
            P.webViewClient = webViewClient;
            return this;
        }
        /**
         *@param onLongClickListener
         *  设置WWebview 长按事件
         */
        public Builder setOnLongClickListener(OnLongClickListener onLongClickListener) {
            P.onLongClickListener = onLongClickListener;
            return this;
        }
        /**
         * @param isSupportAddPhoto 支持调用相机相册进行添加图片（5.0以上）
         */
        public Builder setIsSupportAddPhoto(boolean isSupportAddPhoto) {
            P.isSupportAddPhoto = isSupportAddPhoto;
            return this;
        }

        /**
         * @param ua 设置Webview ua
         */
        public Builder setUserAgentString(String ua) {
            P.ua = ua;
            return this;
        }

        private SixWebView create() {
            SixWebView webView = new SixWebView(P.mContext);
            P.applay(webView.C);
            return webView;
        }

        public SixWebView loadUrl() {
            SixWebView webView = create();
            P.setWebSetting(webView.C);
            return webView;
        }
    }


}
