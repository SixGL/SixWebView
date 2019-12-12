package webview.six.com.web;

//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.module.AppGlideModule;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class SGlideModule extends AppGlideModule {


    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
