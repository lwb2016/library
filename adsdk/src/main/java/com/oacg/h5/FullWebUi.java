package com.oacg.h5;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.oacg.adbase.Constants;
import com.oacg.chromeweb.BaseWebClient;
import com.oacg.chromeweb.x5web.X5WebViewClient;
import com.tencent.smtt.sdk.WebView;

import top.libbase.tool.ResUtils;
import top.libbase.ui.activity.BaseActivity;

public class FullWebUi extends BaseActivity implements BaseWebClient.WebClientListener {

	protected BaseWebClient<WebView> mBaseWebClient;

    FrameLayout mRootView;
    ProgressBar mPbLoading;
    View error_view;

    private String url="http://game.oacg.cn/game_center/v1/index.php?m=Index&a=index&channel_id=1002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initView();
        initUrl(savedInstanceState);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                loadWeb();
            }
        });
    }
    
    protected int getLayoutRes(){
    	return getLayout("ad_full_web_ui");
    }
    
    protected void initUrl(Bundle savedInstanceState){
    	if(savedInstanceState!=null){
    		url=savedInstanceState.getString(Constants.IntentExtra.WEB_URL);
    	}else if(getIntent()!=null){
    		url=getIntent().getStringExtra(Constants.IntentExtra.WEB_URL);
    	}
    	if(TextUtils.isEmpty(url)){    		
    		url=getDefaultUrl();
    	}
    }
    
    protected String getDefaultUrl(){
    	return "http://www.gm88.com/index.php";
    }

    protected void initView() {
		// TODO Auto-generated method stub
    	mRootView=(FrameLayout) findViewById(getId("rl_root"));
    	mPbLoading=(ProgressBar) findViewById(getId("pb_loading"));
    	error_view=findViewById(getId("tv_error"));
    	error_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reloadWeb();
			}
		});
		mBaseWebClient=new X5WebViewClient(mContext,mRootView);
		mBaseWebClient.setClientListener(this);
		mBaseWebClient.onCreate();
	}

	private void loadWeb(){
        if(mBaseWebClient.getWebView()!=null&&!TextUtils.isEmpty(url)){
			mBaseWebClient.getWebView().loadUrl(url);
        }
    }
	
	private void reloadWeb(){
		if(mBaseWebClient.getWebView()!=null){
			mBaseWebClient.getWebView().reload();
		}
	}
	
	@Override
	public void onLoadingStart() {
		// TODO Auto-generated method stub
		error_view.setVisibility(View.GONE);
		mPbLoading.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onLoadingSuccess() {
		// TODO Auto-generated method stub
		mPbLoading.setVisibility(View.GONE);
	}
	
	@Override
	public void onLoadingError() {
		// TODO Auto-generated method stub
		error_view.setVisibility(View.VISIBLE);
	}

	@Override
	public void onReceiveTitle(String s) {

	}

	@Override
	public void onProgressChange(int newProgress) {
		// TODO Auto-generated method stub
		mPbLoading.setProgress(newProgress);
	}

	@Override
	public void startDownload(String s, long l) {

	}

	protected int getLayout(String name){
		return ResUtils.getLayout(name, this);
	}

	protected int getId(String name){
		return ResUtils.getId(name,this);
	}


	@Override
	public void onBackPressed() {
		if(mBaseWebClient.getWebView()!=null&&mBaseWebClient.getWebView().canGoBack()){
			mBaseWebClient.getWebView().goBack();
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		mBaseWebClient.onDestroy();
		super.onDestroy();
	}
}
