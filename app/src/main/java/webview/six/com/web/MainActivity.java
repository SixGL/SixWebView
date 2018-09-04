package webview.six.com.web;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.six.webview.web.SixWebView;
import com.six.webview.web.listener.OnLongClickListener;
import com.six.webview.web.listener.WebChromeClientListener;
import com.six.webview.web.listener.WebViewClientListener;

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
        SixWebView builder = new SixWebView.Builder(this).
                setWebView(mWebview)
                .setSupportJS(true)
                .setUrl(url)
                .setJavaScriptEnabled("haha", new WebJs(this))
                .setWebChromeClient(new WebChromeClientListener() {
                    @Override
                    public void onReceivedTitle(android.webkit.WebView view, String title) {
                        Log.i("MainActivity->", "onReceivedTitle->" + title);
                    }
                })
                .setWebViewClient(new WebViewClientListener() {

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
