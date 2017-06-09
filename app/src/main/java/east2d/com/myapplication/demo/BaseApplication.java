package east2d.com.myapplication.demo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

import com.oacg.ad.AdSDK;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by leo on 2017/4/18.
 */

public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        prepareX5();
        AdSDK.get().init(this);

        addCount((int) (20*Math.random()));

    }

    private void prepareX5(){
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    private void addCount(int count){
        int badgeCount = count;
        ShortcutBadger.applyCount(this, badgeCount); //for 1.1.4+
        //ShortcutBadger.with(getApplicationContext()).count(badgeCount); //for 1.1.3
    }
}
