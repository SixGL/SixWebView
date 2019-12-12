package com.six.webview.web.screenshot;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import com.six.webview.R;


public enum SWebScreenShot {
    INSTANCE;
    private Activity mActivity;
    private WebView mWeb;
    private boolean isLoading = true;
    private boolean isReloadWeb = true;
    private int mDelayTime = 300;

    protected static Handler mHandler = new Handler(Looper.getMainLooper());
    private AlertDialog dialog;

    public SWebScreenShot toInit(Activity ctx, WebView web) {
        mActivity = ctx;
        mWeb = web;
        return this;
    }

    Bitmap longImage;

    public void recycle() {
        if (longImage != null) {
            longImage.recycle();
        }

    }

    /**
     * @param delayTime web截图延迟
     *                  单位:毫秒
     *                  解释:调用buildDrawingCache后，不可以立刻截取，
     *                  否则截取的只是当前界面可见的视图，其他部分会是空白
     */
    public SWebScreenShot setDelayTime(int delayTime) {
        mDelayTime = delayTime;
        return this;
    }

    /**
     * @param loading 默认开启
     *                true : web截图是否需要展示默认loading
     *                如果开发者需要自己定义loading界面，传入false，
     *                在{@link ScreenShotCallback screenShotBefore} 回调中展示你的loading
     *                在{@link ScreenShotCallback screenShotafter} 回调中关闭你的loading
     */
    public SWebScreenShot isScreenSHotLoading(boolean loading) {
        isLoading = loading;
        return this;
    }

    /**
     * @param reload 默认开启
     *               true：web重新加载
     *               截图后会调用mWeb.draw()，导致webview展示的是截取图片，不再是网页所以需要reload
     *               如果开发者自己去决定什么时候重新reload，传入false即可。开发者可以选择合适的时机web重新reload
     */
    public SWebScreenShot isReloadWeb(boolean reload) {
        isReloadWeb = reload;
        return this;
    }

    public SWebScreenShot toScreenSHot() {
        if (mWeb != null) {
            mWeb.measure(
                    View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            mWeb.layout(0, 0, mWeb.getMeasuredWidth(), mWeb.getMeasuredHeight());
            mWeb.buildDrawingCache(true);
        }
        return this;
    }


    public SWebScreenShot toCancvas(final ScreenShotCallback callback) {
        if (callback != null) {
            callback.screenShotBefore();
        }
        if (isLoading) {
            showDialog();
        }
        if (mWeb != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    longImage = Bitmap.createBitmap(mWeb.getMeasuredWidth(), mWeb.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(longImage); // 画布的宽高和 WebView 的网页保持一致
                    Paint paint = new Paint();
                    canvas.drawBitmap(longImage, 0f, mWeb.getMeasuredHeight(), paint);
                    mWeb.draw(canvas);
                    if (isReloadWeb) {
                        Log.e("SixWeb", "isReloadWeb "+isReloadWeb);
                        mWeb.reload();
                    }
                    SaveIm screenShot = new SaveIm();
                    String path = screenShot.save(mActivity, longImage);
                    if (callback != null) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        callback.screenShotResult(path);
                        callback.screenShotafter();
                    } else {
                        Log.e("SixWeb", "ScreenShotCallback is  null");
                    }
                }
            }, mDelayTime);
        }
        return this;
    }


    private void showDialog() {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.s_dialog_loading, null);
        dialog = new AlertDialog.Builder(mActivity)
                .setView(inflate)
                .setCancelable(true)
                .show();
        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = dp2px(90);
        params.height = dp2px(90);
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, mActivity.getResources().getDisplayMetrics());
    }

}
