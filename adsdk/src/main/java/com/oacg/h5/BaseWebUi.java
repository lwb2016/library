package com.oacg.h5;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.oacg.ad.AdHandler;

import east2d.com.tool.PermissionUtil;

/**
 * webview的基类（适用于Ui部分）
 * Created by leo on 2017/4/12.
 */

public class BaseWebUi extends Activity{

    protected WebView mWebView;
    protected Context mContext=BaseWebUi.this;

    protected void newWebView(ViewGroup viewGroup){
        if(viewGroup==null)
            return;
        mWebView=new CusWebView(mContext);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //隐藏滚动条
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewGroup.addView(mWebView);
        //初始化webview
        initWebView();
    }

    protected void initWebView(){
        setSetting();
        setWebViewClient();
        setChromeClient();
        setDownloadListener();
    }

    protected void setSetting(){
        if(mWebView==null)
            return;
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
    
    protected void setDownloadListener(){
    	if(mWebView==null)
            return;
        mWebView.requestFocus();
    	mWebView.setDownloadListener(new DownloadListener() {
			
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
					long contentLength) {
				// TODO Auto-generated method stub
				Log.i("GAME_TEST", url);
                if(PermissionUtil.checkPermissions(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    AdHandler.startDownload(mContext,url,contentLength);
                }else{
                    Toast.makeText(mContext,"SD卡下载权限被拒绝",Toast.LENGTH_SHORT).show();
                    PermissionUtil.requestPermissions(BaseWebUi.this,123, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
			}
		});
    }

    protected void setWebViewClient(){
        if(mWebView==null)
            return;
        mWebView.setWebViewClient(new WebViewClient(){

            private boolean isError=false;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("GAME_TEST22", url);
                if (openWithWebView(url)) {//如果是超链接，执行此方法
                    view.loadUrl(url);
                } else {
                	 Uri uri = Uri.parse(url); // url为你要链接的地址
                	 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                	 startActivity(intent);
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
                Log.i("WEB_TEST","onPageStarted:"+url);
                isError=false;
                onLoadingStart();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("WEB_TEST","onPageFinished");
                if(isError){
                    onLoadingError();
                }else{
                    onLoadingSuccess();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                isError=true;
                Log.i("WEB_TEST","onReceivedError:"+description);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    /**
     * 加载开始时的回调
     */
    protected void onLoadingStart(){}

    /**
     * 加载结束时的回调
     */
    protected void onLoadingSuccess(){}

    /**
     * 加载错误时的回调
     */
    protected void onLoadingError(){}

    protected void setChromeClient(){
        if(mWebView==null)
            return;
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                onReceiveTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                onProgressChange(newProgress);
            }
        });
    }

    protected void onReceiveTitle(String title){}
    protected void onProgressChange(int newProgress){}

    /**
     * 是否支持缓存
     * @return
     */
    private boolean isCacheSupport() {
        return true;
    }

    @Override
    protected void onDestroy() {
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
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            try {
                mWebView.destroy();
            } catch (Throwable ex) {

            }
        }
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if(mWebView!=null&&mWebView.canGoBack()){
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
