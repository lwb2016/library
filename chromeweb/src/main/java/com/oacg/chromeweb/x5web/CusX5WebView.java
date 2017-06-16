package com.oacg.chromeweb.x5web;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * Created by leo on 2017/4/12.
 */

public class CusX5WebView extends WebView {

    public CusX5WebView(Context context) {
        super(context);
    }

    public CusX5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusX5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CusX5WebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public CusX5WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }

    @Override
    public void destroy() {
        getSettings().setBuiltInZoomControls(true);
        super.destroy();
    }
}
