package com.oacg.ad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告的基本数据
 * Created by leo on 2017/4/25.
 */

public class CbAdData {
    private String ad_data;
    private int code;
    private int interval=3;
    private List<AdData> mDataList;

    public String getAd_data() {
        return ad_data;
    }

    public void setAd_data(String ad_data) {
        this.ad_data = ad_data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AdData> getDataList() {
        return mDataList;
    }

    public int getInterval() {
        if(interval<0){
            return 1;
        }
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setDataList(List<AdData> dataList) {
        mDataList = dataList;
    }

    public static CbAdData parseJsonData(Object object){
        if(object==null){
            return null;
        }
        try {
            CbAdData cbAdData=new CbAdData();
            JSONObject jsonObject;
            if(object instanceof  JSONObject){
                jsonObject= (JSONObject) object;
            }else{
                jsonObject=new JSONObject(object.toString());
            }
            cbAdData.setCode(jsonObject.getInt("code"));
            cbAdData.setInterval(jsonObject.optInt("interval"));
            JSONArray ad_data = jsonObject.getJSONArray("ad_data");
            if(ad_data!=null&&ad_data.length()>0){
                cbAdData.setAd_data(ad_data.toString());
                List<AdData> list=new ArrayList<>();
                int length = ad_data.length();
                for(int i=0;i<length;i++){
                    JSONObject data = ad_data.getJSONObject(i);
                    AdData adData = AdData.parseJsonData(data);
                    if(adData!=null){
                        list.add(adData);
                    }
                }
                cbAdData.setDataList(list);
            }
            return cbAdData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
