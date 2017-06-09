package east2d.com.download.manager;

import java.util.ArrayList;

import east2d.com.download.config.DownloadConfig;
import east2d.com.download.data.DownloadDataHelper;
import east2d.com.download.listener.DownloadListenerHelper;
import east2d.com.download.task.DownloadTaskHelper;

/**
 * 下载管理器
 * Created by leo on 2017/4/26.
 */

public class DownloadManage implements DownloadLifeCycle {

    private ArrayList<DownloadLifeCycle> mLifeCycles;


    private DownloadDataHelper mDownloadDataHelper;

    private DownloadTaskHelper mDownloadTaskHelper;

    private DownloadListenerHelper mDownloadListenerHelper;

    private DownloadConfig mDownloadConfig;

    @Override
    public void onCreate() {
        mDownloadConfig=new DownloadConfig();
        mLifeCycles=new ArrayList<>();
        mLifeCycles.add(new DownloadDataHelper());
        mLifeCycles.add(new DownloadTaskHelper());
        mLifeCycles.add(new DownloadListenerHelper());
        for(DownloadLifeCycle lifeCycle:mLifeCycles){
            lifeCycle.onCreate();
        }
    }

    @Override
    public void onDestroy() {
        for(DownloadLifeCycle lifeCycle:mLifeCycles){
            lifeCycle.onDestroy();
        }
    }

    @Override
    public void onException() {
        for(DownloadLifeCycle lifeCycle:mLifeCycles){
            lifeCycle.onException();
        }
    }
}
