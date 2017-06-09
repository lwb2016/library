package com.oacg.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.oacg.adbase.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 系统的下载服务
 * Created by leo on 2017/4/18.
 */

public  abstract class DownLoadService extends Service {
    private DownloadManager manager;
    private DownloadCompleteReceiver receiver;
    protected String BASE_DIR = "/oacg/download/";

    protected Map<String,Long> map=new HashMap<String, Long>();

    protected abstract void handlerFileDownloadComplete(Context context,String path);

    protected abstract String getDirPath();

    protected abstract String getFileName(String url);

    private boolean initDownManager(String url,String name) {
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        down.setAllowedOverRoaming(false);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        down.setMimeType(mimeString);
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        down.setTitle(getFileName(url));
        down.setDescription("下载器");
        down.setVisibleInDownloadsUi(true);
        down.setDestinationInExternalPublicDir(getDirPath(),name);
        long enqueue = manager.enqueue(down);
        map.put(name,enqueue);
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(Constants.IntentExtra.DOWNLOAD_URL);
        long length = intent.getLongExtra(Constants.IntentExtra.DOWNLOAD_LENGTH,0);
        String name=getFileName(url);
        Long aLong = map.get(name);
        if(aLong!=null){
            Toast.makeText(getApplicationContext(),"文件已加入下载列表",Toast.LENGTH_SHORT).show();
            return Service.START_NOT_STICKY;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(getDirPath()),name);
        if(file.exists()){
            if(length>0&&file.length()>=length){
                //文件已存在直接安装
                handlerFileDownloadComplete(this,file.getAbsolutePath());
                return Service.START_NOT_STICKY;
            }else{
                //文件破坏了，先删除文件后 重新进行下载
                deleteFile(file);
            }
        }
            try{
                if(initDownManager(url,name)){
                    Toast.makeText(getApplicationContext(),"开始下载",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"当前无法下载",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent0 = new Intent(Intent.ACTION_VIEW, uri);
                    intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent0);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),"下载失败",Toast.LENGTH_SHORT).show();
                }
            }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }

    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                //打开安装
                if(manager.getUriForDownloadedFile(downId)!=null){
                    String realFilePath = getRealFilePath(context, manager.getUriForDownloadedFile(downId));
                    handlerFileDownloadComplete(context,realFilePath);
                }else{
                    Toast.makeText(context,"下载失败",Toast.LENGTH_SHORT).show();
                }

                //移除下载的记录
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    if(map.get(next)==downId){
                        map.remove(next);
                        break;
                    }
                }

            }
        }
    }

    public String getRealFilePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    public static void deleteFile(File file){
        if(file!=null)
            file.delete();
    }

}
