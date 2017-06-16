package com.oacg.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;

import top.libbase.tool.KEncryption;

/**
 * apk应用下载服务
 * Created by leo on 2017/4/25.
 */

public class ApkDownloadService extends DownLoadService {

    public static void installAPK(Context context, String path) {
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

    @Override
    protected void handlerFileDownloadComplete(Context context, String path) {
        installAPK(context, path);
    }

    @Override
    protected String getDirPath() {
        return BASE_DIR+"apk/";
    }

    @Override
    protected String getFileName(String url) {
        return KEncryption.md5(url)+".apk";
    }
}
