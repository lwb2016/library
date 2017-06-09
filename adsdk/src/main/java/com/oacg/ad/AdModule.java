package com.oacg.ad;


import com.oacg.networkrequest.IRequestCallBack;
import com.oacg.networkrequest.OkHttpRequest;
import java.io.File;
import java.util.concurrent.TimeUnit;

import east2d.com.tool.SPUtils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Ad数据请求模块
 * Created by leo on 2017/4/25.
 */

public class AdModule {

    private OkHttpRequest mHttpRequest;

    private SPUtils spUtils;

    private static AdModule sAdModule;

    static AdModule getModule(){
        if(sAdModule==null){
            synchronized (AdModule.class) {
                if(sAdModule==null){
                    sAdModule=new AdModule();
                }
            }
        }
        return sAdModule;
    }

    private AdModule() {
        File cacheFile = new File(AdSDK.get().getContext().getCacheDir(), "ad_reqcache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 8);

        OkHttpClient client=new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(3, TimeUnit.SECONDS)
                .addInterceptor(new AdCacheInterceptor())
                .addNetworkInterceptor(new AdCacheInterceptor())
                .build();

        mHttpRequest=new OkHttpRequest();
        mHttpRequest.setHttpClient(client);

        spUtils=new SPUtils(AdSDK.get().getContext(), AdConfig.AD_SP_NAME);
    }

    /**
     * 获取广告的数据
     * @param group_id
     * @param listener
     */
    public void reqAdData(final int group_id, final AdDataLoadingListener<AdData> listener){
        mHttpRequest.asyncReqHttpData(AdHttp.getAdDataUrl(group_id), null, OkHttpRequest.GET, new IRequestCallBack() {
            @Override
            public void onOK(int code, String response) {
                if(listener!=null){
                    CbAdData cbAdData = CbAdData.parseJsonData(response);
                    if(cbAdData==null||cbAdData.getAd_data()==null||cbAdData.getAd_data().isEmpty()){
                    	ansisysData(cbAdData, group_id, listener);
                    }else{
                    	saveData(response,group_id);
                        listener.onComplete(cbAdData.getDataList(),cbAdData.getInterval());
                    }
                }
            }

            @Override
            public void onError(int code, String msg) {
                if(listener!=null)
                	ansisysData(null, group_id, listener);
            }
        });
    }
    
   private void ansisysData(CbAdData cbAdData,int group_id,AdDataLoadingListener<AdData> listener){
	   if(cbAdData==null){
		   cbAdData=getDataFromLocal(group_id);
	   }
	   if(cbAdData==null||cbAdData.getAd_data()==null||cbAdData.getAd_data().isEmpty()){
           listener.onError(-1,"no ad data");
       }else{
           listener.onComplete(cbAdData.getDataList(),cbAdData.getInterval());
       }
   }
   
   private CbAdData getDataFromLocal(int group_id){
	   String local=spUtils.getString(AdConfig.AD_SP_CONTENT+group_id, "");
	   return CbAdData.parseJsonData(local);
   }
   
   private void saveData(String content,int groupId){
	   spUtils.putString(AdConfig.AD_SP_CONTENT+groupId, content);
   }
}
