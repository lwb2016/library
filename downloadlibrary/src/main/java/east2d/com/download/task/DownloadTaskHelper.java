package east2d.com.download.task;

import android.text.TextUtils;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import east2d.com.download.listener.DownloadListener;
import east2d.com.download.listener.TaskDataOption;
import east2d.com.download.manager.DownloadLifeCycle;
import east2d.com.download.manager.IUnitDownloadOption;

/**
 * Created by leo on 2017/4/26.
 */

public class DownloadTaskHelper implements DownloadLifeCycle,IUnitDownloadOption<DownloadTask>,DownloadListener,TaskDataOption {
    private Executor mExecutor;
    private Map<String,DownloadTask> mTaskMap;


    public DownloadTaskHelper() {
        mTaskMap = Collections.synchronizedMap(new HashMap<String, DownloadTask>());
    }

    public Executor getExecutor() {
        return mExecutor;
    }

    public void setExecutor(Executor executor) {
        mExecutor = executor;
    }


    @Override
    public DownloadTask get(String url) {
        return null;
    }

    @Override
    public boolean add(String url,String savePath) {
        DownloadTask downloadTask = mTaskMap.get(url);
        if(downloadTask==null&& !TextUtils.isEmpty(url)){
            downloadTask=new DownloadTask(url,savePath);
            downloadTask.setDownloadListener(this);
            downloadTask.setTaskDataOption(this);
            mTaskMap.put(url,downloadTask);
            mExecutor.execute(downloadTask);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String url) {
        DownloadTask downloadTask = mTaskMap.remove(url);
        if(downloadTask!=null){
            downloadTask.cancel();
            return true;
        }
        return false;
    }

    @Override
    public boolean pause(String url) {
        DownloadTask downloadTask = mTaskMap.remove(url);
        if(downloadTask!=null){
            downloadTask.cancel();
            return true;
        }
        return false;
    }

    @Override
    public boolean cancel(String url) {
        DownloadTask downloadTask = mTaskMap.remove(url);
        if(downloadTask!=null){
            downloadTask.cancel();
            return true;
        }
        return false;
    }

    @Override
    public boolean check(String url) {
        return mTaskMap.containsKey(url);
    }

    @Override
    public int getStatus(String url) {
        return 0;
    }

    @Override
    public void setProgress(String url, long current, long max) {

    }

    @Override
    public void onStart(String url, String savePath) {

    }

    @Override
    public void onProgress(String url, String savePath, long progress, long max) {

    }

    @Override
    public void onComplete(String url, String savePath) {

    }

    @Override
    public void onCancel(String url, String savePath) {

    }

    @Override
    public void onFail(String url, String savePath) {

    }

    @Override
    public void onPause(String url, String savePath) {

    }

    @Override
    public String generalFileName(String url) {
        return url;
    }

    @Override
    public long getMax(String url, String savePath) {
        return 0;
    }

    @Override
    public long getAvailable(String url, String savePath) {
        return 0;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onException() {

    }
}
