package com.oacg.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by leo on 2017/6/5.
 */

public class AppInstallReceiver extends BroadcastReceiver {

    public static final String ACTION_DOWNLOAD_INSTALL="com.oacg.receiver.ACTION_DOWNLOAD_INSTALL";
    public static final String EXTRA_DOWNLOAD_INSTALL="EXTRA_DOWNLOAD_INSTALL";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!=null&&intent.getAction().equals(ACTION_DOWNLOAD_INSTALL)){
            String extra = intent.getStringExtra(EXTRA_DOWNLOAD_INSTALL);
            if(!TextUtils.isEmpty(extra)){
                ApkDownloadService.installAPK(context,extra);
            }
        }
    }

    public static PendingIntent getPendIntent(Context context,String path){
        Intent intent=new Intent();
        intent.setAction(ACTION_DOWNLOAD_INSTALL);
        intent.putExtra(EXTRA_DOWNLOAD_INSTALL,path);
        //request必须要不同，否则每次都是以最后一个为主
        return PendingIntent.getBroadcast(context,path.hashCode(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
