package com.oacg.download.manager;

/**
 * Created by leo on 2017/4/26.
 */

public interface DownloadLifeCycle {

    void onCreate();

    void onDestroy();

    void onException();

}
