package com.oacg.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import top.libbase.tool.ResUtils;

/**
 * Created by leo on 2017/6/6.
 */

public class DownloadNotificationService extends Service {

    private Map<String,NotificationCompat.Builder> mBuilderMap;

    private NotificationReceiver registerReceiver;

    private NotificationManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilderMap=new HashMap<String,NotificationCompat.Builder>();
        registerReceiver=new NotificationReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(DownLoadIntentService.ACTION_FILE_DOWNLOAD);
        registerReceiver(registerReceiver,intentFilter);
        Log.i("NotificationService","onCreate");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(registerReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null&&intent.getAction()==DownLoadIntentService.ACTION_FILE_DOWNLOAD){
                int state = intent.getIntExtra(DownLoadIntentService.EXTRA_FILE_DOWNLOAD_STATE,-1);
                String url=intent.getStringExtra(DownLoadIntentService.EXTRA_FILE_DOWNLOAD_URL);
                if(state==DownLoadIntentService.STATE_FILE_START){
                    onStart(url);
                }else if(state==DownLoadIntentService.STATE_FILE_PROGRESS){
                    int progress=intent.getIntExtra(DownLoadIntentService.EXTRA_FILE_DOWNLOAD_PROGRESS,0);
                    onProgress(url,progress);
                }else if(state==DownLoadIntentService.STATE_FILE_COMPLETE){
                    String savePath=intent.getStringExtra(DownLoadIntentService.EXTRA_FILE_DOWNLOAD_SAVE_PATH);
                    onComplete(url,savePath);
                }else if(state==DownLoadIntentService.STATE_FILE_FAIL){
                    onFail(url);
                }
            }
        }
    }

    private void onStart(String url){
        //Log.i("NotificationService","onStart");
        NotificationCompat.Builder builder = mBuilderMap.get(url);
        if(builder==null){
            builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(ResUtils.getDrawable("app_icon",this));
            builder.setContentTitle("游戏下载中");
            builder.setContentText("开始下载。。。");
            builder.setProgress(100, 0, false);
            mBuilderMap.put(url,builder);
        }
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(url.hashCode(), notification);
    }
    private void onProgress(String url,int progress){
        //Log.i("NotificationService","onProgress");
        NotificationCompat.Builder builder = mBuilderMap.get(url);
        if(builder==null)
            return;
        builder.setProgress(100, progress, false);
        builder.setContentText("已下载" + progress + "%");
        //下载进度提示
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(url.hashCode(), notification);
    }
    private void onComplete(String url,String path){
        //Log.i("NotificationService","onComplete");
        NotificationCompat.Builder builder = mBuilderMap.get(url);
        if(builder==null)
            return;
        //下载完成后更改标题以及提示信息
        builder.setContentTitle("游戏下载完成");
        builder.setContentIntent(AppInstallReceiver.getPendIntent(this,path));
        //builder.setContentIntent(DownLoadIntentService.getInstallPendingIntent(this,path));
        //设置进度为不确定，用于模拟安装
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(url.hashCode(), notification);
        ApkDownloadService.installAPK(this, path);
    }
    private void onFail(String url){
        //Log.i("NotificationService","onFail");
        NotificationCompat.Builder builder = mBuilderMap.get(url);
        if(builder==null)
            return;
        //下载完成后更改标题以及提示信息
        builder.setContentTitle("游戏下载失败");
        //设置进度为不确定，用于模拟安装
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(url.hashCode(), notification);
    }
}
