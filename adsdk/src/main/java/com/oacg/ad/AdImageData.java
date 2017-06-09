package com.oacg.ad;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leo on 2017/4/25.
 */

public class AdImageData {
    private String ad_res_url;
    private int image_type ;  //1:icon图片  2：素材图片

    public String getAd_res_url() {
        return ad_res_url;
    }

    public void setAd_res_url(String ad_res_url) {
        this.ad_res_url = ad_res_url;
    }

    public int getImage_type() {
        return image_type;
    }

    public void setImage_type(int image_type) {
        this.image_type = image_type;
    }

    public static AdImageData parseJsonData(Object obj){
        if(obj==null)
            return null;
        try {
            JSONObject jsonObject=null;
            if(obj instanceof JSONObject){
                jsonObject= (JSONObject) obj;
            }else{
                jsonObject=new JSONObject(obj.toString());
            }
            AdImageData imageData=new AdImageData();
            imageData.setAd_res_url(jsonObject.getString("ad_res_url"));
            imageData.setImage_type(jsonObject.getInt("image_type"));
            return imageData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
