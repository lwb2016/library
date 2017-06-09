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

public class SplashAdUi extends Activity implements View.OnClickListener{
    FrameLayout fl_ad;
    TextView tv_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_demo_ui);
        fl_ad= (FrameLayout) findViewById(R.id.fl_ad);
        tv_ad= (TextView) findViewById(R.id.tv_ad);
        showData(fl_ad);

    }

//

    private void showData(final FrameLayout frameLayout){
                AdSDK.get().showSplashAd(SplashAdUi.this,1, frameLayout, new AdLoadingListener() {
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
    }
}
