package com.oacg.download.notification;

import android.support.v4.app.NotificationCompat;

import com.oacg.download.manager.DownloadLifeCycle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo on 2017/4/26.
 */

public class DownloadNotificationHelper implements DownloadLifeCycle {
    private Map<String,NotificationCompat.Builder> notifications;

    @Override
    public void onCreate() {
        notifications=new HashMap<>();
    }

    @Override
    public void onDestroy() {
        notifications.clear();
    }

    @Override
    public void onException() {

    }
}
