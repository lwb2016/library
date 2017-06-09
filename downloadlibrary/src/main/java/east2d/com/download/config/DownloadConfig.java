package east2d.com.download.config;


import android.os.Environment;
import android.text.TextUtils;

import east2d.com.download.DownloadSdk;
import east2d.com.file.StorageData;
import east2d.com.tool.SPUtils;

/**
 * 下载的配置文件
 * Created by leo on 2017/4/26.
 */

public class DownloadConfig {

    //////////////////////////////////////是否显示notification///////////////////////////////////////////////

    /**
     * 下载过程中是否显示通知栏弹窗
     */
    private boolean showNotification=true;


    public boolean isShowNotification() {
        return showNotification;
    }

    public boolean getShowNotification() {
        boolean result = new SPUtils(DownloadSdk.get().getContext(), IDownload.Params.SP_NAME).getBoolean(IDownload.Params.SP_KEY_NOTIFICATION, true);
        showNotification=result;
        return showNotification;
    }

    public void setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
        new SPUtils(DownloadSdk.get().getContext(), IDownload.Params.SP_NAME).getBoolean(IDownload.Params.SP_KEY_NOTIFICATION, showNotification);
    }

    ////////////////////////////////////////////////存储卡的选择程序////////////////////////////////
    private StorageData mStorageData;

    public StorageData getStorageData() {
        if(mStorageData==null){
            return getLocalStorageData();
        }
        return mStorageData;
    }

    public StorageData getLocalStorageData() {
        SPUtils spUtils = new SPUtils(DownloadSdk.get().getContext(), IDownload.Params.SP_NAME);
        String lable=spUtils.getString(IDownload.Params.SP_KEY_STORAGE_NAME);
        String path=spUtils.getString(IDownload.Params.SP_KEY_STORAGE_PATH);
        if(TextUtils.isEmpty(path)){
            mStorageData=new StorageData();
            lable="内置SD卡";
            path=getExternalPath();
        }
        mStorageData.setLabel(lable);
        mStorageData.setPath(path);
        return mStorageData;
    }

    public void setStorageData(StorageData storageData) {
        if(storageData==null||TextUtils.isEmpty(storageData.getPath()))
            return;
        mStorageData = storageData;
        SPUtils spUtils = new SPUtils(DownloadSdk.get().getContext(), IDownload.Params.SP_NAME);
        spUtils.putString(IDownload.Params.SP_KEY_STORAGE_NAME,storageData.getLabel());
        spUtils.putString(IDownload.Params.SP_KEY_STORAGE_PATH,storageData.getPath());
    }

    public String getSavePath(){
        StorageData storageData = getStorageData();
        if(storageData==null||TextUtils.isEmpty(storageData.getPath())){
            return getExternalPath();
        }
        return storageData.getPath();
    }

    public static String DEFAULT_PATH="";

    public String getExternalPath(){
        if(Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return DEFAULT_PATH;
    }
}
