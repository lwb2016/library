package com.oacg.ad;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * 开屏页的广告
 * Created by leo on 2017/4/25.
 */

public class SplashAd extends BaseAdView {
    private ImageView mImageView;

    private AdData mAdData;

    public SplashAd(Context context, FrameLayout frameLayout, AdLoadingListener listener) {
        super(context, frameLayout, listener);
    }

    private void setListener() {
        if(mImageView==null)
            return;
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdHandler.handleAd(mContext,mAdData);
            }
        });
    }

    @Override
    protected void init(AdData data) {
        if(data==null) {
            loadError();
            return;
        }
        String url = data.getFirstBigPicRes();
        if(TextUtils.isEmpty(url)){
            loadError();
            return;
        }
        loadStart();
        mAdData=data;
        initView();

        //加载图片
        Glide.with(mContext).fromString()
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                        loadError();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                        loadComplete();
                        return false;
                    }
                }).into(mImageView);
    }

    private void initView() {
        if(mImageView!=null)
            return;
        mImageView=new ImageView(mContext);
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(layoutParams);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mFrameLayout.addView(mImageView);
        setListener();
    }

    @Override
    protected void clear() {
        if(mImageView==null)
            return;
        ViewGroup parent = (ViewGroup) mImageView.getParent();
        if(parent!=null){
            parent.removeView(mImageView);
        }
        mImageView=null;
    }
}
