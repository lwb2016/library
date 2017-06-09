package east2d.com.myapplication.demo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.oacg.ad.AdData;
import com.oacg.adapter.BaseAdViewPagerAdapter;

import east2d.com.tool.ResUtils;

/**
 * Created by leo on 2017/4/20.
 */

public class DemoAdPageAdapter extends BaseAdViewPagerAdapter<String,AdData,DemoAdPageAdapter.ItemVH,DemoAdPageAdapter.ADVH> {


    public DemoAdPageAdapter(Context context, List<String> items, List<AdData> ads) {
        super(context, items, ads);
    }

    @Override
    protected ADVH getADViewHolder(LayoutInflater layoutInflater, ViewGroup container) {
        View inflate = layoutInflater.inflate(ResUtils.getLayout("ad_plug_item",mContext), null);
        return new ADVH(inflate,mContext);
    }

    @Override
    protected ItemVH getItemViewHolder(LayoutInflater layoutInflater, ViewGroup container) {
        View view=new TextView(mContext);
        return new ItemVH(view);
    }

    @Override
    protected void boundItemViewHolder(ItemVH baseViewHolder, String e, int itemPostion, int position) {
        baseViewHolder.itemView.setBackgroundColor(Color.parseColor("#00FF00"));
        ((TextView)baseViewHolder.itemView).setText(e);
    }

    @Override
    protected void boundADViewHolder(ADVH baseViewHolder, AdData adData, int adPostion, int position) {
        Glide.with(mContext).load(adData.getFirstBigPicRes()).into( baseViewHolder.mImageView);
        Glide.with(mContext).load(adData.getIconRes()).into( baseViewHolder.iv_icon);
        baseViewHolder.tv_title.setText(adData.getAd_title());
        //((TextView)baseViewHolder.itemView).setText(adData.getAd_url());
    }

    public static class ADVH extends BaseAdViewPagerAdapter.ADViewHolder{

        ImageView mImageView,iv_icon;
        TextView tv_title;

        public ADVH(View itemView,Context context) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(ResUtils.getId("iv_ad_big",context));
            iv_icon= (ImageView) itemView.findViewById(ResUtils.getId("iv_ad",context));
            tv_title= (TextView) itemView.findViewById(ResUtils.getId("tv_ad",context));
        }
    }

    public static class ItemVH extends BaseAdViewPagerAdapter.ItemViewHolder{

        public ItemVH(View itemView) {
            super(itemView);
        }
    }
}
