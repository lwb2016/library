package com.oacg.chromeweb.chrome;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by leo on 2017/4/12.
 */

public class CusWebView extends WebView {
    public CusWebView(Context context) {
        super(context);
    }

    public CusWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void destroy() {
        getSettings().setBuiltInZoomControls(true);
        super.destroy();
    }
}
