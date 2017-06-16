package com.oacg.ad;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.oacg.adbase.Constants;
import com.oacg.h5.GameFullWebUi;
import com.oacg.h5.GameTitleWebUi;
import com.oacg.service.DownLoadIntentService;

import top.libbase.tool.PermissionUtil;
import top.libbase.ui.handler.BaseUiHandler;
import top.libbase.ui.handler.SystemUiHandler;


/**
 * 广告的事件响应器
 * Created by leo on 2017/4/25.
 */

public class AdHandler extends BaseUiHandler {
    /**
     * 处理广告的数据结果
     * @param context
     * @param adData
     */
    public static void handleAd(Context context,AdData adData){
        if(adData==null)
            return;
        switch (adData.getAd_type()){
            case 1: //1：安卓客户端游戏
                startDownload(context,adData.getAd_url(),-1);
                break;
            case 2:  //2：H5游戏
                startWeb(context, adData);
                break;
            case 3:  //3：安卓客户端App
                startDownload(context,adData.getAd_url(),-1);
                break;
            case 4:  //4：H5广告链接
                startWeb(context, adData);
                break;
            case 9999:  //9999：无点击效果
            default:
                break;
        }
    }

    /**
     * 启动web页面
     * @param context
     * @param adData
     */
    public static void startWeb(Context context,AdData adData){
        if(adData==null)
            return;
        switch (adData.getAd_webview_type()){
            case 0://0 站外
                SystemUiHandler.startOutsideWeb(context,adData.getAd_url());
                break;
            case 1:  //1 站内（有标题）
                startInsideTitleWeb(context,adData.getAd_url());
                break;
            case 2:  //2 站内（无标题）
                startInsideFullWeb(context,adData.getAd_url());
                break;
            default:
                break;
        }
    }

    /**
     * 打开内部无标题网页
     * @param context
     * @param url
     */
    public static void startInsideFullWeb(Context context,String url){
        if(TextUtils.isEmpty(url))
            return;
        Intent intent=new Intent(context, GameFullWebUi.class);
        intent.putExtra(Constants.IntentExtra.WEB_URL,url);
        startActivity(context,intent);
    }

    /**
     * 打开内部有标题的网页
     * @param context
     * @param url
     */
    public static void startInsideTitleWeb(Context context,String url){
        if(TextUtils.isEmpty(url))
            return;
        Intent intent=new Intent(context, GameTitleWebUi.class);
        intent.putExtra(Constants.IntentExtra.WEB_URL,url);
        startActivity(context,intent);
    }

    /**
     * 下载文件
     * @param context
     * @param url
     * @param contentLength
     */
    public static void startDownload(Context context,String url,long contentLength){
        if(PermissionUtil.checkPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            DownLoadIntentService.startDownload(context,url);
        }else{
            Toast.makeText(context,"读写权限暂不可用,请授权后重试",Toast.LENGTH_SHORT).show();
            if(context instanceof Activity){
                PermissionUtil.requestPermissions((Activity) context,AdConfig.AD_PERMISSION_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
    }
}
