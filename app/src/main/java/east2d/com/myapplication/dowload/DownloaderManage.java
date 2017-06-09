package east2d.com.myapplication.dowload;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by leo on 2017/4/18.
 */

public class DownloaderManage {

    private Context mContext;

    /**
     * 用于存储所有的下载数据
     */
    private Map<String,DownloadData> mDataMap=new HashMap<>();

    String TAG=DownloaderManage.class.getSimpleName();

    Executors mExecutors;

    Map<String,Runnable> mRunnableMap=new HashMap<>();

    public DownloaderManage() {
        mExecutors= (Executors) Executors.newFixedThreadPool(3);
    }

    public void addDownloadUrl(String url){
        Runnable runnable = mRunnableMap.get(url);
        if(runnable!=null){
            Log.i(TAG,"已加入下载队列");
        }
    }

    public void init(Context context){
        mContext=context;
    }

    public boolean add(String url){
        if(TextUtils.isEmpty(url))
            return false;
        DownloadData downloadData = mDataMap.get(url);
        if(downloadData==null){
            downloadData=new DownloadData();
            downloadData.setDownload_path(url);
            downloadData.setSave_path("");
        }
        return false;
    }
}
