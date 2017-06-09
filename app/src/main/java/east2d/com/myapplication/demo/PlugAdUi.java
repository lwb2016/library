package east2d.com.myapplication.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import east2d.com.myapplication.R;
import com.oacg.ad.AdLoadingListener;
import com.oacg.ad.AdSDK;

/**
 * 开屏广告测试
 * Created by leo on 2017/4/25.
 */

public class PlugAdUi extends Activity implements View.OnClickListener{
    FrameLayout fl_ad;
    TextView tv_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_demo_ui);
        fl_ad= (FrameLayout) findViewById(R.id.fl_ad);
        tv_ad= (TextView) findViewById(R.id.tv_ad);
        //findViewById(R.id.btn_1).setOnClickListener(this);
        //findViewById(R.id.btn_2).setOnClickListener(this);
        //findViewById(R.id.btn_3).setOnClickListener(this);
        //findViewById(R.id.btn_4).setOnClickListener(this);

        showData(fl_ad);
    }

    private void showData(FrameLayout frameLayout){

        AdSDK.get().showPlusAd(PlugAdUi.this, 3,frameLayout, new AdLoadingListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
//        switch (id){
//            case R.id.btn_1:
//                loadingData(1);
//                break;
//            case R.id.btn_2:
//                loadingData(2);
//                break;
//            case R.id.btn_3:
//                loadingData(3);
//                break;
//            case R.id.btn_4:
//                loadingData(4);
//                break;
//        }
    }
}
