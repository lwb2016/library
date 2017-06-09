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

public class AdData {
    private long ad_id;  //广告id

    private int weight=0;

    private int ad_type;  //广告类型 （1：安卓客户端游戏；2：H5游戏；3：安卓客户端App；4：H5广告链接；9999：无点击效果）

    private String ad_title;  //广告标题

    private String ad_subtitle;  //广告副标题

    private List<AdImageData> ad_res_urls;  //广告的图片资源(数组)  //

    private String ad_url;  //广告链接（下载地址、网页地址）

    private int ad_webview_type;  //网页地址的打开方式（0 站外；1 站内（有标题）；2 站内（无标题））

    public long getAd_id() {
        return ad_id;
    }

    public void setAd_id(long ad_id) {
        this.ad_id = ad_id;
    }

    public int getAd_type() {
        return ad_type;
    }

    public void setAd_type(int ad_type) {
        this.ad_type = ad_type;
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public String getAd_subtitle() {
        return ad_subtitle;
    }

    public void setAd_subtitle(String ad_subtitle) {
        this.ad_subtitle = ad_subtitle;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public int getAd_webview_type() {
        return ad_webview_type;
    }

    public void setAd_webview_type(int ad_webview_type) {
        this.ad_webview_type = ad_webview_type;
    }

    public List<AdImageData> getAd_res_urls() {
        return ad_res_urls;
    }

    public void setAd_res_urls(List<AdImageData> ad_res_urls) {
        this.ad_res_urls = ad_res_urls;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getIconRes(){
        if(ad_res_urls==null||ad_res_urls.isEmpty())
            return null;
        for(AdImageData data:ad_res_urls){
            if(data.getImage_type()==1){
                return data.getAd_res_url();
            }
        }
        return null;
    }

    public String getFirstBigPicRes(){
        if(ad_res_urls==null||ad_res_urls.isEmpty())
            return null;
        for(AdImageData data:ad_res_urls){
            if(data.getImage_type()==2){
                return data.getAd_res_url();
            }
        }
        return null;
    }

    public static  AdData parseJsonData(Object obj){
        if(obj==null)
            return null;
        try {
            JSONObject jsonObject=null;
            if(obj instanceof JSONObject){
                jsonObject= (JSONObject) obj;
            }else{
                jsonObject=new JSONObject(obj.toString());
            }
            AdData adData=new AdData();
            adData.setAd_id(jsonObject.getLong("id"));
            adData.setAd_title(jsonObject.getString("ad_title"));
            adData.setAd_type(jsonObject.getInt("ad_type"));
            adData.setWeight(jsonObject.getInt("weight"));
            adData.setAd_subtitle(jsonObject.getString("ad_subtitle"));
            adData.setAd_url(jsonObject.getString("ad_url"));
            adData.setAd_webview_type(jsonObject.getInt("ad_webview_type"));
            JSONArray ad_image_arr = jsonObject.getJSONArray("ad_image_arr");
            if(ad_image_arr!=null&&ad_image_arr.length()>0){
                List<AdImageData> list=new ArrayList<AdImageData>();
                int length = ad_image_arr.length();
                for(int i=0;i<length;i++){
                    AdImageData adResUrl = AdImageData.parseJsonData(ad_image_arr.getJSONObject(i));
                    if(adResUrl!=null){
                        list.add(adResUrl);
                    }
                }
                adData.setAd_res_urls(list);
            }
            return adData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
