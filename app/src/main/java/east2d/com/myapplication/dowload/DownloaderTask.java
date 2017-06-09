package east2d.com.myapplication.dowload;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by leo on 2017/4/18.
 */

public class DownloaderTask implements Runnable{

    DownloadData mDownloadData;


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

    private boolean isContinue=true;

    public boolean isContinue() {
        return isContinue;
    }

    public DownloaderTask setContinue(boolean aContinue) {
        isContinue = aContinue;
        return this;
    }

    private int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * 设置连接超时时间
     */
    public DownloaderTask setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    private int getReadTimeout() {
        return readTimeout;
    }

    /**
     * 设置读取超时时间
     */
    public DownloaderTask setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }


    private String getRequestMethod() {
        return requestMethod;
    }

    /**
     * 设置请求的方法
     */
    public DownloaderTask setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }


    @Override
    public void run() {
        if(mDownloadData==null)
            return;
        //开始下载
        String encodedUrl = Uri.encode(mDownloadData.getDownload_path(), "@#&=*+-_.,:!?()/~\'%");
        HttpURLConnection conn =null;
        InputStream is=null;
        RandomAccessFile fos=null;
        try {
            conn = (HttpURLConnection)(new URL(encodedUrl)).openConnection();
            conn.setRequestMethod(getRequestMethod());
            conn.setConnectTimeout(getConnectTimeout());
            if(getReadTimeout()>0){
                conn.setReadTimeout(getReadTimeout());
            }
            conn.connect();
            if(conn.getResponseCode()== HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                File parent=new File(mDownloadData.getSave_path());
                if(parent.exists()||parent.mkdirs()){
                    //创建临时文件进行存储
                    File tempFile=new File(parent,mDownloadData.getName()+".temp");
                    fos = new RandomAccessFile(tempFile,"rwd");
                    long max = conn.getContentLength();
                    long progress=0;
                    //判断是否进行继续下载
                    if(isContinue()){
                        progress = mDownloadData.getProgress();
                    }else{
                        progress = 0;
                    }
                    FileChannel channel = fos.getChannel();
                    MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, progress, max);
                    //跳转到下载的地方
                    fos.seek(progress);
                    fos.setLength(max);
                    mDownloadData.setProgress(progress);
                    mDownloadData.setMax(max);
                    //下载中
                    byte[] buffer = new byte[1024];
                    int length=0;
                    while ((length = is.read(buffer)) !=-1) {
                        map.put(buffer,0,length);
                        progress += length;
                        mDownloadData.setProgress(progress);
                    }
                    //文件下载完成后的处理
                    if(tempFile.exists()){
                        File file = new File(parent,mDownloadData.getName());
                        //如果当前文件已存在则删除该文件
                        if(!file.exists()||file.delete()){
                            if(tempFile.renameTo(file)){
                                //完成下载后将临时文件重命名为该文件

                            }
                        }
                    }
                }
            }
        } catch (Exception e1) {
            Log.i("download","Exception"+ e1.getMessage());
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
    }


}
