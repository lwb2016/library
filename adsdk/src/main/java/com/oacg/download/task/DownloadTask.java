package com.oacg.download.task;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.oacg.download.exception.DownloadException;
import com.oacg.download.listener.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by leo on 2017/4/26.
 */

public class DownloadTask implements Runnable,DownloadListener{
    private String url;
    private String savePath;
    private DownloadListener mDownloadListener;

    public DownloadTask(String url, String savePath) {
        this.url = url;
        this.savePath = savePath;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    @Override
    public void run() {
        if(TextUtils.isEmpty(url)||TextUtils.isEmpty(savePath))
            throw new DownloadException("Empty url or Empty save path");
        onStart(url,savePath);
        int result = doDownload();
        switch (result){
            case PROGRESS:
            case ERROR:
                onFail(url,savePath);
                break;
            case COMPLETE:
                onComplete(url,savePath);
                break;
            case CANCEL:
            default:
                onCancel(url,savePath);
                break;
        }
    }

    public static final int PROGRESS=0;
    public static final int COMPLETE=1;
    public static final int ERROR=2;
    public static final int CANCEL=-1;

    protected int doDownload() {
        int result=PROGRESS;  //0未下载完成 ；1下载成功 ；2 下载出错 ；-1下载取消
        //开始下载
        String encodedUrl = Uri.encode(url, "@#&=*+-_.,:!?()/~\'%");
        HttpURLConnection conn =null;
        InputStream is=null;
        RandomAccessFile fos=null;
        //创建临时文件进行存储
        String fileName=generalFileName(url);

        File parent=new File(savePath);
        File file = new File(parent,fileName);
        if(file.exists()){
           return COMPLETE;
        }
        File tempFile=new File(parent,fileName+".temp");
        try {
            if(isCancel())
                return CANCEL;
            conn = (HttpURLConnection)(new URL(encodedUrl)).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.connect();
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
                    is = conn.getInputStream();
                    if(parent.exists()||parent.mkdirs()){
                        //创建临时文件进行存储
                        fos = new RandomAccessFile(tempFile,"raw");

                        long max = conn.getContentLength();
                        long progress = 0;
                        if(max==getMax(url,savePath)){
                            progress=getAvailable(url,savePath);
                        }
                        fos.seek(progress);

                        is.skip(progress);

                        byte[] buffer = new byte[1024];
                        int length;
                        if(isCancel())
                            return CANCEL;
                        while ((length = is.read(buffer)) !=-1) {
                            if(isCancel())
                                return CANCEL;
                            progress += length;
                            fos.write(buffer, 0, length);
                            onProgress(url,savePath,progress,max);
                            //fos.();
                        }
                        if(tempFile.exists()){
                            //如果当前文件已存在则删除该文件
                            if(!file.exists()||file.delete()){
                                if(tempFile.renameTo(file)){
                                    //完成下载后将临时文件重命名为该文件
                                    result=COMPLETE;
                                }
                            }
                        }
                    }
            }
        } catch (Exception e) {
            Log.i("download","Exception"+ e.getMessage());
//            if(tempFile.exists()){
//                tempFile.delete();
//            }
            result=ERROR;
        } finally {
            //关闭输入输出流
            try {
                if(fos!=null)
                    fos.close();
            } catch (IOException e1) {

            }
            try {
                if(is!=null)
                    is.close();
            } catch (IOException e1) {

            }
            if(conn!=null)
                conn.disconnect();
        }
        return result;
    }

    @Override
    public String generalFileName(String url) {
        if(mDownloadListener!=null)
            return mDownloadListener.generalFileName(url);
        return String.valueOf(url.hashCode());
    }


    @Override
    public void onStart(String url, String savePath) {
        if(mDownloadListener!=null)
             mDownloadListener.onStart(url,savePath);
    }

    @Override
    public void onProgress(String url, String savePath,long progress,long max) {
        if(mDownloadListener!=null)
            mDownloadListener.onProgress(url,savePath,progress,max);
    }

    @Override
    public void onComplete(String url, String savePath) {
        if(mDownloadListener!=null)
            mDownloadListener.onComplete(url,savePath);
    }

    @Override
    public void onCancel(String url, String savePath) {
        if(mDownloadListener!=null)
            mDownloadListener.onCancel(url,savePath);
    }

    @Override
    public void onFail(String url, String savePath) {
        if(mDownloadListener!=null)
            mDownloadListener.onFail(url,savePath);
    }

    @Override
    public long getMax(String url, String savePath) {
        if(mDownloadListener!=null)
            return mDownloadListener.getMax(url,savePath);
        return 0;
    }

    @Override
    public long getAvailable(String url, String savePath) {
        if(mDownloadListener!=null)
            return mDownloadListener.getAvailable(url,savePath);
        return 0;
    }

    private boolean isCancel =false;

    public void cancel() {
        isCancel =true;
    }

    public boolean isCancel() {
        return isCancel;
    }
}
