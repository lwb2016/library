package east2d.com.download.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;

/**
 * 软件安装的广播接收类
 * Created by leo on 2017/6/5.
 */

public class AppInstallReceive extends BroadcastReceiver {
    public static final String APP_INSTALL_ACTION="com.oacg.action.APP_INSTALL_ACTION";
    public static final String APP_INSTALL_ADDED="android.intent.action.PACKAGE_ADDED";
    public static final String APP_INSTALL_REMOVE="android.intent.action.PACKAGE_REMOVED";

    public static final String APP_INSTALL_EXTRA_PATH="APP_INSTALL_EXTRA_PATH"; //软件的安装路径
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent==null)
            return;
        String action=intent.getAction();
        if(action.equals(APP_INSTALL_ACTION)){  //监听软件安装地址并进行安装
            Log.i("homer", "开始安装软件 ");
            installApp(context,intent.getStringExtra(APP_INSTALL_EXTRA_PATH));
        }else if(action.equals(APP_INSTALL_ADDED)){//监听软件安装的状态
            String packageName = intent.getDataString();
            Log.i("homer", "安装了 :" + packageName);
        }else if(action.equals(APP_INSTALL_REMOVE)){//监听软件删除的状态
            String packageName = intent.getDataString();
            Log.i("homer", "卸载了 :" + packageName);
        }
    }

    public static PendingIntent getPendIntent(Context context, String path){
        Intent intent=new Intent();
        intent.setAction(APP_INSTALL_ACTION);
        intent.putExtra(APP_INSTALL_EXTRA_PATH,path);
        //requestCode 尽量不要设置固定值，会覆盖前一个同样请求码的请求
        return PendingIntent.getBroadcast(context,path.hashCode(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void sendBroadcast(Context context, String path){
        Intent intent=new Intent();
        intent.setAction(APP_INSTALL_ACTION);
        intent.putExtra(APP_INSTALL_EXTRA_PATH,path);
        context.sendBroadcast(intent);
    }

    public static void installApp(Context context,String path){
        if(TextUtils.isEmpty(path)||!path.endsWith(".apk"))
            return;
        File file = new File(path);
        if (file.exists()) {
            openApkFile(file, context);
        } else {
            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *重点在这里
     */
    public static void openApkFile(File var0, Context var1) {
        Intent var2 = new Intent();
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>=24){
            Uri uriForFile = FileProvider.getUriForFile(var1, var1.getApplicationContext().getPackageName() + ".provider", var0);
            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
        }else{
            var2.setDataAndType(Uri.fromFile(var0), getMIMEType(var0));
        }
        try {
            var1.startActivity(var2);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(var1, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

}
