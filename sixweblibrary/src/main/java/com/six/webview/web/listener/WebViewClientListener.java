package com.six.webview.web.listener;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * Created by admin on 2018/9/4.
 */

public abstract class WebViewClientListener {


    //默认返回false
    public  boolean shouldOverrideUrlLoading(WebView view, String url){

        return false;
    }

    public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {

    }


    public void onPageFinished(WebView view, String url) {

    }
}
