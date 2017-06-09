package east2d.com.download;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * 文件下载器（自定义）
 * Created by leo on 2016/10/28.
 */
public class FileDownloader extends AsyncTask<Void,Long,String> implements Runnable{
    /**
     * 下载地址，保存地址，保存文件名
     */
    private String downloadPath,savePath,fileName;

    /**
     * 下载器的监听
     */
    private OnDownloadListener mListener;


    /**
     * 连接超时
     */
    private int connectTimeout=5000;

    /**
     * 读入超时
     */
    private int readTimeout=-1;

    /**
     * 网络请求的方式
     */
    private String requestMethod="GET";

    public FileDownloader() {

    }

    public void setParams(String downloadPath, String savePath, String fileName){
        this.downloadPath = downloadPath;
        this.savePath = savePath;
        this.fileName = fileName;
    }

    private int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * 设置连接超时时间
     */
    public FileDownloader setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    private int getReadTimeout() {
        return readTimeout;
    }

    /**
     * 设置读取超时时间
     */
    public FileDownloader setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }


    private String getRequestMethod() {
        return requestMethod;
    }

    /**
     * 设置请求的方法
     */
    public FileDownloader setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    /**
     * 设置下载的监听
     */
    public FileDownloader setOnDownloadListener(OnDownloadListener listener){
        mListener=listener;
        return this;
    }

    public OnDownloadListener getListener() {
        if(mListener==null){
            mListener=new OnDownloadListener() {
                @Override
                public void startDownload(String downloadPath) {

                }

                @Override
                public void downloadOnProgress(String downloadPath, long progress, long max) {

                }

                @Override
                public void completeDownload(String downloadPath, String filePath) {

                }

                @Override
                public void cancelDownload(String downloadPath) {

                }

                @Override
                public void downloadFail(String downloadPath) {

                }
            };
        }
        return mListener;
    }

    public void run(){
        onPreExecute();
        String s = doDownload();
        onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        try {
                getListener().startDownload(downloadPath);
        } catch (Exception e) {
                getListener().downloadFail(downloadPath);
        }
    }

    protected String doDownload() {
        if(downloadPath==null)
            return null;
        String path=null;
        //开始下载
        String encodedUrl = Uri.encode(downloadPath, "@#&=*+-_.,:!?()/~\'%");
        HttpURLConnection conn =null;
        InputStream is=null;
        FileOutputStream fos=null;
        File parent=new File(savePath);
        //创建临时文件进行存储
        File tempFile=new File(parent,"temp"+fileName);
        try {
            conn = (HttpURLConnection)(new URL(encodedUrl)).openConnection();
            conn.setRequestMethod(getRequestMethod());
            conn.setConnectTimeout(getConnectTimeout());
            if(getReadTimeout()>0){
                conn.setReadTimeout(getReadTimeout());
            }
            conn.connect();
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                if(parent.exists()||parent.mkdirs()){
                    //创建临时文件进行存储
                    fos = new FileOutputStream(tempFile);
                    long max = conn.getContentLength();
                    long progress = 0;
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) !=-1) {
                        progress += length;
                        fos.write(buffer, 0, length);
                        getListener().downloadOnProgress(downloadPath,progress,max);
                        //publishProgress(progress,max);
                        fos.flush();
                    }
                    if(tempFile.exists()){
                        File file = new File(parent,fileName);
                        //如果当前文件已存在则删除该文件
                        if(!file.exists()||file.delete()){
                            if(tempFile.renameTo(file)){
                                //完成下载后将临时文件重命名为该文件
                                path=file.getAbsolutePath();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            //Log.i("download","Exception"+ e.getMessage());
            if(tempFile.exists()){
                tempFile.delete();
            }
            path=null;
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
        return path;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(downloadPath==null)
            return null;
        String path=null;
        //开始下载
        String encodedUrl = Uri.encode(downloadPath, "@#&=*+-_.,:!?()/~\'%");
        HttpURLConnection conn =null;
        InputStream is=null;
        FileOutputStream fos=null;
        File parent=new File(savePath);
        //创建临时文件进行存储
        File tempFile=new File(parent,"temp"+fileName);

        try {
            conn = (HttpURLConnection)(new URL(encodedUrl)).openConnection();
            conn.setRequestMethod(getRequestMethod());
            conn.setConnectTimeout(getConnectTimeout());
            if(getReadTimeout()>0){
                conn.setReadTimeout(getReadTimeout());
            }
            conn.connect();
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();

                if(parent.exists()||parent.mkdirs()){

                    fos = new FileOutputStream(tempFile);
                    long max = conn.getContentLength();
                    long progress = 0;
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) !=-1) {
                        progress += length;
                        fos.write(buffer, 0, length);
                        publishProgress(progress,max);
                        fos.flush();
                    }
                    if(tempFile.exists()){
                        File file = new File(parent,fileName);
                        //如果当前文件已存在则删除该文件
                        if(!file.exists()||file.delete()){
                            if(tempFile.renameTo(file)){
                                //完成下载后将临时文件重命名为该文件
                                path=file.getAbsolutePath();
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e1) {
            Log.i("download","FileNotFoundException" + e1.getMessage());
            path=null;
        } catch (MalformedURLException e1) {
        	Log.i("download","MalformedURLException"+ e1.getMessage());
            path=null;
        } catch (ProtocolException e1) {
        	Log.i("download","ProtocolException"+ e1.getMessage());
            path=null;
        } catch (IOException e1) {
        	Log.i("download","IOException"+ e1.getMessage());
            path=null;
        } catch (Exception e1) {
        	Log.i("download","Exception"+ e1.getMessage());
            path=null;
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
        return path;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
            getListener().downloadOnProgress(downloadPath,values[0],values[1]);
    }

    @Override
    protected void onPostExecute(String s) {
        if(s!=null){
            getListener().completeDownload(downloadPath,s);
        }else{
            getListener().downloadFail(downloadPath);
            deleteFile();
        }
    }

    @Override
    protected void onCancelled() {
        getListener().cancelDownload(downloadPath);
        deleteFile();
    }

    /**
     * 对外提供的下载接口
     */
    public interface OnDownloadListener{
        /**
         * 开始下载
         */
        void startDownload(String downloadPath);

        /**
         * 正在下载
         */
        void downloadOnProgress(String downloadPath, long progress, long max);

        /**
         * 下载完成
         */
        void completeDownload(String downloadPath, String filePath);

        /**
         * 下载取消
         */
        void cancelDownload(String downloadPath);

        /**
         * 下载失败
         */
        void downloadFail(String downloadPath);
    }

    private boolean deleteFile(){
        //下载失败后如果下载文件失败则删除下载文件
        File file=new File(savePath,"temp"+fileName);
        if(!file.exists()||file.delete()){
            return true;
        }
        return false;
    }
}
