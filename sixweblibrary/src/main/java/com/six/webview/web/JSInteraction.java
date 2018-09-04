package com.six.webview.web;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/4.
 */

public class JSInteraction implements Serializable {
    private Context mContext;

    public JSInteraction(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }
}
