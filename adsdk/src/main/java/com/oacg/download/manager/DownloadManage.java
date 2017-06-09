package com.oacg.download.manager;

import com.oacg.download.config.DownloadConfig;
import com.oacg.download.listener.DownloadListenerHelper;
import com.oacg.download.task.DownloadTaskHelper;
import com.oacg.download.data.DownloadDataHelper;

/**
 * 下载管理器
 * Created by leo on 2017/4/26.
 */

public class DownloadManage implements DownloadLifeCycle {

    private DownloadDataHelper mDownloadDataHelper;

    private DownloadTaskHelper mDownloadTaskHelper;

    private DownloadListenerHelper mDownloadListenerHelper;

    private DownloadConfig mDownloadConfig;

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
