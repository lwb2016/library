package com.oacg.ad;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 广告SDK
 * Created by leo on 2017/4/25.
 */

public class AdSDK {
    private static AdSDK mAdSDK;

    public synchronized static AdSDK get(){
        if (mAdSDK==null){
            synchronized (AdSDK.class){
                if(mAdSDK==null){
                    mAdSDK=new AdSDK();
                }
            }
        }
        return mAdSDK;
    }

    private AdModule mAdModule;

    private AdSDK() {
    }

    private Context mContext;

    public void init(Context context){
        if(mContext==null) {
            mContext = context.getApplicationContext();
        }
        mAdModule=AdModule.getModule();
    }

    public Context getContext(){
        return mContext;
    }

    public AdModule getAdModule() {
        return mAdModule;
    }

    /**
     * 显示开屏广告
     * @param context
     * @param frameLayout
     * @param listener
     */
    public void showSplashAd(Context context,int group_id, FrameLayout frameLayout, AdLoadingListener listener){
        new SplashAdViewModule().show(context,group_id, frameLayout, listener);
    }

    /**
     * 显示插屏广告
     * @param context
     * @param frameLayout
     * @param listener
     */
    public void showPlusAd(Context context, int group_id,FrameLayout frameLayout,  AdLoadingListener listener){
        new PlugAdViewModule().show(context,group_id, frameLayout, listener);
    }

}
