package east2d.com.myapplication.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import east2d.com.myapplication.R;
import com.oacg.ad.AdData;
import com.oacg.ad.AdDataLoadingListener;
import com.oacg.ad.AdHandler;
import com.oacg.ad.AdModule;
import com.oacg.ad.AdSDK;
import com.oacg.adapter.BaseAdPagerAdapter;
import com.oacg.adapter.BaseAdViewPagerAdapter;

/**
 * Created by leo on 2017/4/19.
 */

public class ViewPagerUi extends Activity {
    ViewPager mViewPager;
    DemoAdPageAdapter mImagePagerAdapter;

    private Context mContext=ViewPagerUi.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vp_ui);
        mViewPager= (ViewPager) findViewById(R.id.vp_imgs);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mImagePagerAdapter=new DemoAdPageAdapter(this,null,null);
        mImagePagerAdapter.setListener(new BaseAdViewPagerAdapter.OnItemClickListener<AdData, String>() {
            @Override
            public void onItemClick(View view, String s, int adPostion, int allPosition) {

            }

            @Override
            public void onAdItemClick(View view, AdData t, int itemPostion, int allPosition) {
                AdHandler.handleAd(mContext,t);
            }
        });

        mImagePagerAdapter.setNum(3);
        mImagePagerAdapter.setAllowAd(true);

        mViewPager.setAdapter(mImagePagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(ViewPagerUi.this, "selected:"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        items=new ArrayList<>();
        int num=1;
        for(int i=0;i<100;i++){
            items.add("item"+(num++));
        }
        loadingData(4);

    }

    private boolean isLoading=false;

    private ArrayList<String> items;

    private void loadingData(final int type){
        if(isLoading) {
            Toast.makeText(this,"正在加载。。",Toast.LENGTH_SHORT).show();
            return;
        }
        isLoading=true;
        AdSDK.get().getAdModule().reqAdData(type,false, new AdDataLoadingListener<AdData>() {
            @Override
            public void onError(int code, final Object error) {
                isLoading=false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext,"广告"+type+"加载失败："+error,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onComplete(final List<AdData> list, final int space) {
                isLoading=false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImagePagerAdapter.setNum(space);
                        mImagePagerAdapter.resetData(items,list);
                    }
                });
            }
        });
    }
}
