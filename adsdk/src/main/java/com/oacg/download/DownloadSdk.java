package com.oacg.download;

import android.content.Context;

/**
 * 下载的接口
 * Created by leo on 2017/4/26.
 */

public class DownloadSdk {

    private static DownloadSdk mDownloadSdk;

    public static synchronized DownloadSdk get(){
        if(mDownloadSdk==null){
            synchronized (DownloadSdk.class){
                if(mDownloadSdk==null){
                    mDownloadSdk=new DownloadSdk();
                }
            }
        }
        return mDownloadSdk;
    }

    private Context mContext;

    public void init(Context context){
        if(mContext==null&&context!=null){
            mContext=context.getApplicationContext();
        }
    }

    public Context getContext() {
        return mContext;
    }
}
