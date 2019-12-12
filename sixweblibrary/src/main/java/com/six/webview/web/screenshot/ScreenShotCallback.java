package com.six.webview.web.screenshot;

import android.graphics.Bitmap;

public abstract class ScreenShotCallback {
    /**
     * @param path   插入相册后的路径
     */
    public abstract void screenShotResult(String path);

    public void screenShotBefore() {
    }

    public void screenShotafter() {
    }
}
