package webview.six.com.web;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;


public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s);
        ImageView imageView = findViewById(R.id.imageView);
        String url = getIntent().getStringExtra("url");
        Log.e("截取路径 ", url);
      ;
        Glide.with(this).load(new File(url)).into(imageView);
//        Glide.with(this).load("http://image.bjzxcp.com/images/cash/095811_4e39fcb310d82.jpg").apply(requestOptions).into(imageView);
    }
}
