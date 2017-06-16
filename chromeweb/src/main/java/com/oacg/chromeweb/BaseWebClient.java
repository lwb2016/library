package com.oacg.chromeweb;

import android.content.Context;
import android.view.View;
/**
 * Created by leo on 2017/6/13.
 */

public abstract class BaseWebClient<T extends View> {
    protected Context mContext;
    protected T mWebView;
    protected WebClientListener mClientListener;

    public BaseWebClient(Context context, T webView) {
        mContext = context;
        mWebView = webView;
    }

    public abstract void onCreate();
    public abstract void onDestroy();
    public abstract void addJavascriptInterface(Object object, String name);

    public T getWebView() {
        return mWebView;
    }

    public void setClientListener(WebClientListener clientListener) {
        mClientListener = clientListener;
    }

    public interface WebClientListener{
        void onLoadingStart();
        void onLoadingSuccess();
        void onLoadingError();
        void onReceiveTitle(String title);
        void onProgressChange(int newProgress);
        void startDownload(String url,long contentLength);
    }

}
