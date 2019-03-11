package com.six.webview.web.listener;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

/**
 * Created by admin on 2018/9/4.
 */

public abstract class WebChromeClientListener {

    public void onReceivedTitle(android.webkit.WebView view, String title) {

    }
    /**
     * @支持webview上传图片
     * showFileChooserCallBack 兼容性方法>5.0
     * */
//    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
//
//    }

    public void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

    }
}
