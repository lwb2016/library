package com.oacg.download.data;

import com.oacg.download.manager.IUnitDownloadOption;
import com.oacg.download.manager.DownloadLifeCycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下载数据管理器
 * Created by leo on 2017/4/26.
 */

public class DownloadDataHelper implements DownloadLifeCycle,IUnitDownloadOption {

    private Map<String,DownloadData> dataMap;

    public DownloadData getDownloadData(String url){
        if(!dataMap.containsKey(url)){

        }
        return dataMap.get(url);
    }

    public List<DownloadData> getAllData(){
        return new ArrayList<>(dataMap.values());
    }

    @Override
    public void onCreate() {
        dataMap=new HashMap<>();
        List<DownloadData> downloadDatas = queryData();
        if(downloadDatas!=null){
            for(DownloadData data:downloadDatas){
                dataMap.put(data.getUrl(),data);
            }
        }
    }

    private List<DownloadData> queryData(){
        //查询所有的下载列表
        return null;
    }

    @Override
    public void onDestroy() {
        dataMap.clear();
    }

    @Override
    public void onException() {
        //保存数据

        //退出
    }

    /////////////////////////////////////////////////////////Option///////////////////

    @Override
    public boolean add(String url,String savePath) {
        if(!dataMap.containsKey(url)){
            DownloadData data=new DownloadData();
            data.setUrl(url);
            data.setSave_path(savePath);
            dataMap.put(url,data);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String url) {
        return false;
    }

    @Override
    public boolean pause(String url) {
        return false;
    }

    @Override
    public boolean cancel(String url) {
        return false;
    }

    @Override
    public boolean check(String url) {
        return false;
    }

    @Override
    public int getStatus(String url) {
        return 0;
    }
}
