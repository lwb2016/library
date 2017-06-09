package com.oacg.ad;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * 开屏页的广告
 * Created by leo on 2017/4/25.
 */

public class PlugAd extends BaseAdView {
    private View mView;
    private ImageView mImageView;
    private TextView mTextView;

    private AdData mAdData;

    private FrameLayout.LayoutParams layoutParams;

    public void setParams(int gravity,int margin_left,int margin_top,int margin_right,int margin_bottom){
        getLayoutParams();
        layoutParams.gravity=gravity;
        layoutParams.leftMargin=margin_left;
        layoutParams.topMargin=margin_top;
        layoutParams.rightMargin=margin_right;
        layoutParams.bottomMargin=margin_bottom;
    }

    private FrameLayout.LayoutParams getLayoutParams(){
        if(layoutParams==null){
            layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity=Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
            layoutParams.leftMargin=0;
            layoutParams.topMargin=0;
            layoutParams.rightMargin=0;
            layoutParams.bottomMargin=0;
        }
        return layoutParams;
    }

    public PlugAd(Context context, FrameLayout frameLayout, AdLoadingListener listener){
        super(context, frameLayout, listener);
    }

    private void initView(){
        if(mView!=null)
            return;
        mView= LayoutInflater.from(mContext).inflate(getLayout("ad_plug_layout"),null);
        mView.setLayoutParams(getLayoutParams());

        mTextView= (TextView) mView.findViewById(getId("tv_ad"));

        mImageView= (ImageView) mView.findViewById(getId("iv_ad"));
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mFrameLayout.addView(mView);

        setListener();
    }

    @Override
    protected void init(AdData adData) {
        if(adData==null){
            loadError();
            return;
        }
        String url = adData.getIconRes();
        if(TextUtils.isEmpty(url)){
            loadError();
            return;
        }
        loadStart();
        mAdData=adData;
        initView();
        mTextView.setText(adData.getAd_title());

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

    private void setListener() {
        if(mView==null)
            return;
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdHandler.handleAd(mContext,mAdData);
            }
        });
    }

    @Override
    protected void clear(){
        if(mImageView==null)
            return;
        ViewGroup parent = (ViewGroup) mImageView.getParent();
        if(parent!=null){
            parent.removeView(mImageView);
        }
        mView=null;
    }
}
