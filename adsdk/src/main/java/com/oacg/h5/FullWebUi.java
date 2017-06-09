package com.oacg.h5;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.oacg.adbase.Constants;

import east2d.com.tool.ResUtils;

public class FullWebUi extends BaseX5WebUi {

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
        newWebView(mRootView);
	}

	private void loadWeb(){
        if(mWebView!=null&&!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }
    }
	
	private void reloadWeb(){
		if(mWebView!=null){
			mWebView.reload();
		}
	}
	
	@Override
	protected void onLoadingStart() {
		// TODO Auto-generated method stub
		super.onLoadingStart();
		error_view.setVisibility(View.GONE);
		mPbLoading.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onLoadingSuccess() {
		// TODO Auto-generated method stub
		super.onLoadingSuccess();
		mPbLoading.setVisibility(View.GONE);
	}
	
	@Override
	protected void onLoadingError() {
		// TODO Auto-generated method stub
		super.onLoadingError();
		error_view.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onProgressChange(int newProgress) {
		// TODO Auto-generated method stub
		super.onProgressChange(newProgress);
		mPbLoading.setProgress(newProgress);
	}

	protected int getLayout(String name){
		return ResUtils.getLayout(name, this);
	}

	protected int getId(String name){
		return ResUtils.getId(name,this);
	}
}
