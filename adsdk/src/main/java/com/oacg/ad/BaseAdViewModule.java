package com.oacg.ad;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * Created by leo on 2017/4/25.
 */

public abstract class BaseAdViewModule {

    protected BaseAdView mAdView;

    public abstract void show(Context context,int group_id , FrameLayout frameLayout, AdLoadingListener listener);

    public void dismiss(){
        if(mAdView==null)
            return;
        mAdView.dismiss();
    }

}
