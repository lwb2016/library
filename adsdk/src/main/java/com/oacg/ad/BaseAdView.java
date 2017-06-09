package com.oacg.ad;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;

import east2d.com.tool.ResUtils;

/**
 * Created by leo on 2017/4/25.
 */
public abstract class BaseAdView {
    protected Context mContext;

    protected FrameLayout mFrameLayout;

    protected AdLoadingListener mListener;

    protected Handler mHandler=new Handler(Looper.getMainLooper());

    public BaseAdView(Context context, FrameLayout frameLayout, AdLoadingListener listener) {
        if(context==null)
            throw new AdException("context is null");
        if(frameLayout==null)
            throw new AdException("root view is null");
        mContext = context;
        mFrameLayout = frameLayout;
        mListener = listener;
    }

    protected abstract void init(AdData data);
    protected abstract void clear();

    public void show(final AdData data){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                init(data);
            }
        });
    }

    public void dismiss(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                clear();
            }
        });
    }

    public void loadError(){
        if(mListener==null)
            return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onError();
            }
        });
    }

    public void loadStart(){
        if(mListener==null)
            return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onStart();
            }
        });
    }
    public void loadComplete(){
        if(mListener==null)
            return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onComplete();
            }
        });
    }

    protected int getLayout(String name){
        return ResUtils.getLayout(name,mContext);
    }

    protected int getId(String name){
        return ResUtils.getId(name,mContext);
    }
}
