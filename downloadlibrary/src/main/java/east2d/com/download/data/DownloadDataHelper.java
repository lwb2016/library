package east2d.com.download.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import east2d.com.download.config.IDownload;
import east2d.com.download.db.DbDataHelper;
import east2d.com.download.manager.DownloadLifeCycle;
import east2d.com.download.manager.IUnitDownloadOption;

/**
 * 下载数据管理器
 * Created by leo on 2017/4/26.
 */

public class DownloadDataHelper implements DownloadLifeCycle,IUnitDownloadOption<DownloadData> {

    private Map<String,DownloadData> dataMap;

    private DbDataHelper mDbDataHelper;

    @Override
    public void onCreate() {
        mDbDataHelper=new DbDataHelper();
        mDbDataHelper.onCreate();
        dataMap=new HashMap<>();
        List<DownloadData> downloadDatas = mDbDataHelper.queryAllData();
        if(downloadDatas!=null){
            for(DownloadData data:downloadDatas){
                dataMap.put(data.getUrl(),data);
            }
        }
    }

    @Override
    public void onDestroy() {
        dataMap.clear();
        mDbDataHelper.onDestroy();
    }

    @Override
    public void onException() {
        //保存数据
        //退出
        mDbDataHelper.onException();
    }

    /////////////////////////////////////////////////////////Option///////////////////

    @Override
    public DownloadData get(String url) {
        DownloadData data = dataMap.get(url);
        if(data==null){
            data = mDbDataHelper.query(url);
            if(data!=null){
                dataMap.put(data.getUrl(),data);
            }
        }
        return data;
    }

    @Override
    public boolean add(String url,String savePath) {
        if(!dataMap.containsKey(url)){
            DownloadData data=new DownloadData();
            data.setUrl(url);
            data.setSave_path(savePath);
            //
            data = mDbDataHelper.add(data);//数据库添加
            dataMap.put(data.getUrl(),data);//缓存
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String url) {
        DownloadData remove = dataMap.remove(url);
        if(remove!=null){
            //清除数据库中的数据
            mDbDataHelper.delete(remove);
            return true;
        }
        return false;
    }

    @Override
    public boolean pause(String url) {
        return setStatus(url,IDownload.TaskStatus.PAUSE);
    }

    @Override
    public boolean cancel(String url) {
        return setStatus(url,IDownload.TaskStatus.CANCEL);
    }

    @Override
    public boolean check(String url) {
        return get(url)!=null;
    }

    @Override
    public int getStatus(String url) {
        DownloadData data = get(url);
        return data==null?IDownload.TaskStatus.NONE:data.getStatus();
    }

    @Override
    public void setProgress(String url, long current, long max) {
        DownloadData data = get(url);
        if(data!=null){
            data.setDown_length(current);
            data.setAll_length(max);
            //更新数据库
            //更新数据库
            mDbDataHelper.update(data);
        }
    }

    private boolean setStatus(String url, int status){
        DownloadData data = get(url);
        if(data!=null){
            data.setStatus(IDownload.TaskStatus.PAUSE);
            //更新数据库
            mDbDataHelper.update(data);
            return true;
        }
        return false;
    }
}
