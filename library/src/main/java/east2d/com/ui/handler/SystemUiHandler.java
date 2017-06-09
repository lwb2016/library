package east2d.com.ui.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

import east2d.com.tool.UriUtil;


/**
 * Created by leo on 2017/4/25.
 */

public class SystemUiHandler extends BaseUiHandler {
    /**
     * 打开外部网页
     * @param context
     * @param url
     */
    public static void startOutsideWeb(Context context, String url){
        if(TextUtils.isEmpty(url))
            return;
        try {
            Uri uri=Uri.parse(url);
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(context,intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 指定调用相机拍照后照片的储存路径
     * @param context
     * @param path
     */
    public static void startOutsideWeb(Activity context, String path,int requestCode){
        File imgFile = new File(path);
        Uri imgUri = UriUtil.getUriByFile(context,imgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        context.startActivityForResult(intent, requestCode);
    }


    /**
     * 发起剪裁图片的请求
     * @param activity 上下文
     * @param srcFile 原文件的File
     * @param output 输出文件的File
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(Activity activity, File srcFile, File output,int requestCode) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        //intent.setDataAndType(Uri.fromFile(srcFile), "image/*");
        intent.setDataAndType(UriUtil.getImageContentUri(activity,srcFile), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 480);

        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        activity.startActivityForResult(intent, requestCode);
    }
}
