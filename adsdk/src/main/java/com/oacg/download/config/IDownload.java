package com.oacg.download.config;

/**
 * Created by leo on 2017/4/26.
 */

public interface IDownload {

    interface TaskStatus{
        int RUN=1;  //正在运行下载
        int WAIT=2;  //等待下载中
        int PAUSE=3;  //暂停下载中
        int DELETE=4;  //已删除
        int CANCEL=5;   //下载已取消
        int NONE=0;
    }

    interface SDStatus{
        int INTER=1;
        int EXTENT=2;
    }


    interface Params{
        String SP_NAME="leo_download_sp_name";
        String SP_KEY_NOTIFICATION="SP_KEY_NOTIFICATION";
        String SP_KEY_STORAGE_NAME="SP_KEY_STORAGE_NAME";
        String SP_KEY_STORAGE_PATH="SP_KEY_STORAGE_PATH";
    }

}
