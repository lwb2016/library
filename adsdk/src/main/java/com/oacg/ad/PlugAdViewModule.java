package com.oacg.ad;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by leo on 2017/4/25.
 */

public class PlugAdViewModule extends BaseAdViewModule {

    @Override
    public void show(Context context,int group_id ,FrameLayout frameLayout, AdLoadingListener listener) {
        try {
            if(mAdView==null){
                mAdView =new PlugAd(context, frameLayout, listener);
                ((PlugAd)mAdView).setParams(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0,0,100);
            }
        } catch (Exception e) {
            if(listener!=null){
                listener.onError();
            }
        }
        AdSDK.get().getAdModule().reqAdData(group_id,false, new AdDataLoadingListener<AdData>() {
            @Override
            public void onError(int code, final Object error) {
                if(mAdView!=null){
                    mAdView.loadError();
                }
            }

            @Override
            public void onComplete(List<AdData> list, int space) {
                showView(list.get(0));
            }
        });

    }

    private void showView(AdData data){
        if(mAdView==null)
            return;
        mAdView.show(data);
    }
}
