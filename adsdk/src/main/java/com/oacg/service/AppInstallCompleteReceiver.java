package com.oacg.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by leo on 2017/6/5.
 */

public class AppInstallCompleteReceiver extends BroadcastReceiver {

    public static final String ACTION_DOWNLOAD_INSTALL_COMPLETE="android.intent.action.PACKAGE_ADDED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!=null&&intent.getAction().equals(ACTION_DOWNLOAD_INSTALL_COMPLETE)){
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll();
        }
    }
}
