package east2d.com.download.notification;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;

import east2d.com.download.listener.DownloadListener;
import east2d.com.download.manager.DownloadLifeCycle;

/**
 * Created by leo on 2017/4/26.
 */

public class DownloadNotificationHelper implements DownloadLifeCycle,DownloadListener {
    private Map<String,NotificationCompat.Builder> notifications;
    private Handler mHandler=new Handler(Looper.getMainLooper());

    private Context applicationContext;

    private  final String ON_START="开始下载";
    private  final String ON_PROGRESS="正在下载。。。";
    private  final String ON_COMPLETE="下载完成";
    private  final String ON_CANCEL="下载取消";
    private  final String ON_FAIL="下载失败";
    private  final String ON_PAUSE="下载暂停";

    public DownloadNotificationHelper(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

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

    @Override
    public void onStart(final String url, String savePath) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder builder = notifications.get(url);
                if(builder==null){
                    builder=new NotificationCompat.Builder(applicationContext);
                    notifications.put(url,builder);
                }
                builder.setContentTitle(ON_START);
            }
        });
    }

    @Override
    public void onProgress(final String url, String savePath, final long progress, final long max) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder builder = notifications.get(url);
                if(builder!=null){
                    builder.setContentTitle(ON_PROGRESS);
                    builder.setProgress(100,(int) (progress*100/max),true);
                }
            }
        });
    }

    @Override
    public void onComplete(final String url, String savePath) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder builder = notifications.get(url);
                if(builder!=null){
                    builder.setContentTitle(ON_COMPLETE);
                }
            }
        });
    }

    @Override
    public void onCancel(final String url, String savePath) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder builder = notifications.get(url);
                if(builder!=null){
                    builder.setContentTitle(ON_CANCEL);
                }
            }
        });
    }

    @Override
    public void onFail(final String url, String savePath) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder builder = notifications.get(url);
                if(builder!=null){
                    builder.setContentTitle(ON_FAIL);
                }
            }
        });
    }

    @Override
    public void onPause(final String url, String savePath) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder builder = notifications.get(url);
                if(builder!=null){
                    builder.setContentTitle(ON_PAUSE);
                }
            }
        });
    }
}
