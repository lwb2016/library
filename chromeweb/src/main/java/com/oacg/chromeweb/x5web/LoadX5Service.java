package com.oacg.chromeweb.x5web;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * X5加载服务（获取可以直接在application中设置）
 * Created by leo on 2017/4/10.
 */
public class LoadX5Service extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            // TODO Auto-generated method stub
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            Log.d("app_X5", " onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            // TODO Auto-generated method stub
            Log.d("app_X5", " onCoreInitFinished ");
        }
    };
}
