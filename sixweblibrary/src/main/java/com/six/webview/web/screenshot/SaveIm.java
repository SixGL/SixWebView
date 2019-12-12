package com.six.webview.web.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

import static android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE;

public class SaveIm {

    public String save(Activity context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "sweb");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + "web" + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SWeb: saveIm", file.getAbsolutePath());
        context.sendBroadcast(new Intent(ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        return file.getPath();

    }
}
