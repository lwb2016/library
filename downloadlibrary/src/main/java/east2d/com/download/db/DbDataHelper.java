package east2d.com.download.db;

import java.util.List;

import east2d.com.download.config.IDownload;
import east2d.com.download.data.DownloadData;
import east2d.com.download.manager.DownloadLifeCycle;

/**
 * 下载数据管理器
 * Created by leo on 2017/4/26.
 */

public class DbDataHelper implements DownloadLifeCycle {

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onException() {
        //保存数据

        //退出
    }

    /////////////////////////////////////////////////////////Option///////////////////

    public DownloadData query(String url) {
        DownloadData data=new DownloadData();
        data.setUrl(url);
        return data;
    }
    public List<DownloadData> queryAllData() {
        return null;
    }

    public DownloadData add(DownloadData data) {
        data=new DownloadData();
        data.setStatus(IDownload.TaskStatus.WAIT);
        return data;
    }

    public DownloadData update(DownloadData data) {
        return data;
    }

    public DownloadData delete(DownloadData data){
        return data;
    }

}
