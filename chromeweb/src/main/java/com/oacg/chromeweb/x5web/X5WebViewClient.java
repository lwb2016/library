package com.oacg.chromeweb.x5web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.oacg.chromeweb.BaseWebClient;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * 腾讯X5自带浏览器的设置
 * Created by leo on 2017/6/13.
 */

public class X5WebViewClient extends BaseWebClient<WebView> {
    private String TAG="X5WebViewClient";

    public X5WebViewClient(Context context, @NonNull WebView webView) {
        super(context, webView);
    }

    public X5WebViewClient(Context context, @NonNull ViewGroup viewGroup) {
        super(context, null);
        newWebView(viewGroup);
    }

    private void newWebView(ViewGroup viewGroup) {
        if(viewGroup==null)
            return;
        mWebView=new CusX5WebView(viewGroup.getContext());
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //隐藏滚动条
        mWebView.getView().setHorizontalScrollBarEnabled(false);
        mWebView.getView().setVerticalScrollBarEnabled(false);
        mWebView.getView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewGroup.addView(mWebView);
    }
    /**
     * 对于所有页面尽量设置缓存，如果H5服务端需要读取缓存，而本地又没有设置缓存的时候，直接会导致本地的读取异常
     * @return
     */
    protected boolean isCacheSupport() {
        return true;
    }

    @Override
    public void onCreate() {
        if(mWebView==null)
            return;
        setSetting();
        setWebViewClient();
        setChromeClient();
        setDownloadListener();
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void addJavascriptInterface(Object object, String name) {
        if(mWebView!=null){
            mWebView.addJavascriptInterface(object, name);
        }
    }

    @Override
    public void onDestroy() {
        //销毁webview
        if(mWebView!=null){
            mWebView.stopLoading();
            //从父容器中移除webview
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if(parent!=null){
                parent.removeView(mWebView);
            }
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            //mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            try {
                mWebView.destroy();
            } catch (Throwable ex) {

            }
        }
        if(mContext!=null){
            mContext=null;
        }
    }

    protected void setSetting(){
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        //webSetting.setSupportMultipleWindows(true);  //设置为true后不能支持文件下载功能
        if(isCacheSupport()){
            webSetting.setAppCacheEnabled(true);
            webSetting.setDomStorageEnabled(true);
            webSetting.setGeolocationEnabled(true);
            webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        }
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

//X5 QQ浏览器官方的配置
//        WebSettings webSetting = mWebView.getSettings();
//        webSetting.setJavaScriptEnabled(true);
//        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSetting.setAllowFileAccess(true);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
//        webSetting.setUseWideViewPort(true);
//        webSetting.setSupportMultipleWindows(true);
//        webSetting.setLoadWithOverviewMode(true);
//        webSetting.setAppCacheEnabled(true);
//        webSetting.setDatabaseEnabled(true);
//        webSetting.setDomStorageEnabled(true);
//        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
//        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(getDir("databases", 0).getPath());
//        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0).getPath());
//        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().sync();
    }

    protected void setWebViewClient(){
        mWebView.setWebViewClient(new WebViewClient(){

            private boolean isError=false;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, url);
                if (openWithWebView(url)) {//如果是超链接，执行此方法
                    view.loadUrl(url);
                } else {
                    if(mContext!=null){
                        Uri uri = Uri.parse(url); // url为你要链接的地址
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        mContext.startActivity(intent);
                    }
                }
                return true;

            }

            private boolean openWithWebView(String url) {
                if(url!=null&&(url.startsWith("http")||url.startsWith("https"))){
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i(TAG,"onPageStarted:"+url);
                isError=false;
                if(mClientListener!=null){
                    mClientListener.onLoadingStart();
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG,"onPageFinished");
                if(isError){
                    if(mClientListener!=null){
                        mClientListener.onLoadingError();
                    }
                }else{
                    if(mClientListener!=null){
                        mClientListener.onLoadingSuccess();
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                isError=true;
                Log.i(TAG,"onReceivedError:"+description);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    protected void setChromeClient(){
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(mClientListener!=null){
                    mClientListener.onReceiveTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(mClientListener!=null){
                    mClientListener.onProgressChange(newProgress);
                }
            }
        });
    }

    protected void setDownloadListener(){
        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                // TODO Auto-generated method stub
                Log.i(TAG, url);
                if(mClientListener!=null){
                    mClientListener.startDownload(url, contentLength);
                }
            }
        });
    }
}
