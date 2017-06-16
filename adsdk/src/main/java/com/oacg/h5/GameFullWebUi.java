package com.oacg.h5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.oacg.ad.AdHandler;
import com.oacg.service.DownLoadIntentService;

public class GameFullWebUi extends FullWebUi {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
    }

    private void registerReceiver()
    {
        mDownloadReceiver=new DownloadReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownLoadIntentService.ACTION_FILE_DOWNLOAD);
        registerReceiver(mDownloadReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mDownloadReceiver);
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return getLayout("ad_game_full_web_ui");
	}
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		initDownloadView();
	}

    public void startDownload(String url, long contentLength) {
        Log.i("GAME_TEST", "startDownload:"+url);
        //DownLoadIntentService.startDownload(mContext,url);
        AdHandler.startDownload(mContext,url,contentLength);
    }


    private DownloadReceiver mDownloadReceiver;

    private View download_root;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    protected void initDownloadView() {
        //设置监听
        download_root = findViewById(getId("apk_download_root"));
        mProgressBar = (ProgressBar) findViewById(getId("pb_apk_download"));
        mTextView = (TextView) findViewById(getId("tv_apk_download"));

        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
    }

    class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null&&intent.getAction().equals(DownLoadIntentService.ACTION_FILE_DOWNLOAD)){
                Message message=new Message();
                int state = intent.getIntExtra(DownLoadIntentService.EXTRA_FILE_DOWNLOAD_STATE,-1);
                message.what=state;
                message.obj=intent.getStringExtra(DownLoadIntentService.EXTRA_FILE_DOWNLOAD_URL);
                if(state==DownLoadIntentService.STATE_FILE_PROGRESS){
                    message.arg1=intent.getIntExtra(DownLoadIntentService.EXTRA_FILE_DOWNLOAD_PROGRESS,0);
                }
                mHandler.sendMessage(message);
            }
        }
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DownLoadIntentService.STATE_FILE_START:
                    download_root.setVisibility(View.VISIBLE);
                   // show(msg.obj.hashCode());
                    break;
                case DownLoadIntentService.STATE_FILE_PROGRESS:
                    setDownloadProgress(msg.arg1);
                    //setNotifyProgress(msg.obj.hashCode(),msg.arg1);
                    break;
                case DownLoadIntentService.STATE_FILE_COMPLETE:
                case DownLoadIntentService.STATE_FILE_FAIL:
                    download_root.setVisibility(View.GONE);
                   // setNotifyResult(msg.obj.hashCode(),"下载失败","");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void setDownloadProgress(int progress) {
    	if(download_root.getVisibility()!=View.VISIBLE){
    		download_root.setVisibility(View.VISIBLE);
    	}
        mTextView.setText("游戏下载中..." + progress + "%");
        if(progress<4){
            progress=4;
        }
        mProgressBar.setProgress(progress);
    }
}
