package com.oacg.download.task;

import android.text.TextUtils;

import com.oacg.download.listener.DownloadListener;
import com.oacg.download.manager.IUnitDownloadOption;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by leo on 2017/4/26.
 */

public class DownloadTaskHelper implements IUnitDownloadOption,DownloadListener{
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
    public boolean add(String url,String savePath) {
        DownloadTask downloadTask = mTaskMap.get(url);
        if(downloadTask==null&& !TextUtils.isEmpty(url)){
            downloadTask=new DownloadTask(url,savePath);
            downloadTask.setDownloadListener(this);
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
}
