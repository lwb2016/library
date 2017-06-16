package com.oacg.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import top.libbase.download.FileDownloader;
import top.libbase.tool.KEncryption;
import top.libbase.tool.LogU;

/**
 * 系统的下载服务
 * Created by leo on 2017/4/18.
 */

public class DownLoadIntentService extends IntentService {
    //static Handler mHandler=new Handler(Looper.getMainLooper());

    private static final String ACTION_DOWNLOAD_SERVICE ="intentservice.action.ACTION_DOWNLOAD_SERVICE";
    private static final String ACTION_APK_INSTALL_SERVICE ="intentservice.action.ACTION_INSTALL";
    private static final String EXTRA_DOWNLOAD_SERVICE_URL ="EXTRA_DOWNLOAD_SERVICE_URL";
    public static final String EXTRA_DOWNLOAD_INSTALL="EXTRA_DOWNLOAD_INSTALL";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DownLoadIntentService() {
        super("DownLoadIntentService");
    }


    public static void startDownload(Context context, String path) {
        Intent intent = new Intent(context, DownLoadIntentService.class);
        intent.setAction(ACTION_DOWNLOAD_SERVICE);
        intent.putExtra(EXTRA_DOWNLOAD_SERVICE_URL, path);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent!=null){
            String action = intent.getAction();
            if(action.equals(ACTION_DOWNLOAD_SERVICE)){
                String extra = intent.getStringExtra(EXTRA_DOWNLOAD_SERVICE_URL);
                if(extra.endsWith(".apk")){
                	//Log.i("APK_DOWNLOAD", extra+"1");
                    startDownload(intent.getStringExtra(EXTRA_DOWNLOAD_SERVICE_URL));
                }
            }else if(action.equals(ACTION_APK_INSTALL_SERVICE)){
                String extra = intent.getStringExtra(EXTRA_DOWNLOAD_INSTALL);
                if(!TextUtils.isEmpty(extra)){
                    ApkDownloadService.installAPK(this,extra);
                }
            }
        }
    }

    public static PendingIntent getInstallPendingIntent(Context context,String path){
        Intent intent=new Intent(context,DownLoadIntentService.class);
        intent.setAction(ACTION_APK_INSTALL_SERVICE);
        intent.putExtra(EXTRA_DOWNLOAD_INSTALL,path);
        return PendingIntent.getService(context,path.hashCode(),intent,PendingIntent.FLAG_CANCEL_CURRENT);
    }
    
    protected String BASE_DIR = "/oacg/download/apk";
    
    public static String getSdRootDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return Environment.getDataDirectory().getAbsolutePath();
        }
    }
    
    protected String getDirPath() {
        return getSdRootDir()+BASE_DIR;
    }

    protected String getFileName(String url) {
        return KEncryption.md5(url);
    }
    

    private void startDownload(String url){
        if(TextUtils.isEmpty(url))
            return;
        String filePath = getDirPath();
        String fileName = getFileName(url) + ".apk";
        final File file = new File(filePath, fileName);
        if (file.exists()) {
            ApkDownloadService.installAPK(this, file.getAbsolutePath());
            return;
        }
            FileDownloader fileDownloader = new FileDownloader();
            fileDownloader.setOnDownloadListener(new FileDownloader.OnDownloadListener() {

                @Override
                public void startDownload(final String downloadPath) {
                    onStart(downloadPath);
                }
                private int last=-1;

                @Override
                public void downloadOnProgress(final String downloadPath, long progress, long max) {
                    final int i = (int) (progress * 100 / max);
                    if(i!=last){
                        last=i;
                        onProgress(downloadPath,i);
                    }
                }

                @Override
                public void completeDownload(final String downloadPath, final String filePath) {
                    onComplete(downloadPath,filePath);
                }

                @Override
                public void cancelDownload(final String downloadPath) {
                    onFail(downloadPath);
                }

                @Override
                public void downloadFail(final String downloadPath) {
                    onFail(downloadPath);
                }
            });
        fileDownloader.setParams(url, filePath, fileName);
        fileDownloader.run();
    }

    public static final String ACTION_FILE_DOWNLOAD="ACTION_FILE_DOWNLOAD";

    public static final String EXTRA_FILE_DOWNLOAD_PROGRESS ="EXTRA_FILE_DOWNLOAD_PROGRESS";
    public static final String EXTRA_FILE_DOWNLOAD_SAVE_PATH="ACTION_FILE_DOWNLOAD_SAVE_PATH";
    public static final String EXTRA_FILE_DOWNLOAD_URL="ACTION_FILE_DOWNLOAD_URL";
    public static final String EXTRA_FILE_DOWNLOAD_STATE="EXTRA_FILE_DOWNLOAD_STATE";

    public static final int STATE_FILE_START =0x00101;
    public static final int STATE_FILE_PROGRESS =0x00102;
    public static final int STATE_FILE_COMPLETE =0x00103;
    public static final int STATE_FILE_FAIL =0x00104;

    private void onStart(String url){
        LogU.i("APK_DOWNLOAD", "onStart");
        Intent intent=new Intent();
        intent.setAction(ACTION_FILE_DOWNLOAD);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_STATE, STATE_FILE_START);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_URL,url);
        sendBroadcast(intent);
    }

    private void onProgress(String url,int progress){
        LogU.i("APK_DOWNLOAD", "onProgress:"+progress);
        Intent intent=new Intent();
        intent.setAction(ACTION_FILE_DOWNLOAD);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_STATE, STATE_FILE_PROGRESS);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_PROGRESS,progress);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_URL,url);
        sendBroadcast(intent);
    }

    private void onComplete(String url,String path){
        LogU.i("APK_DOWNLOAD", "onComplete");
        Intent intent=new Intent();
        intent.setAction(ACTION_FILE_DOWNLOAD);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_STATE, STATE_FILE_COMPLETE);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_SAVE_PATH,path);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_URL,url);
        sendBroadcast(intent);
    }
    private void onFail(String url){
    	LogU.i("APK_DOWNLOAD", "onFail");
        Intent intent=new Intent();
        intent.setAction(ACTION_FILE_DOWNLOAD);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_STATE, STATE_FILE_FAIL);
        intent.putExtra(EXTRA_FILE_DOWNLOAD_URL,url);
        sendBroadcast(intent);
    }
}
