package east2d.com.ui.handler;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;

import east2d.com.tool.GetImagePath;
import east2d.com.tool.PermissionUtil;
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

    /**
     * 打开照相机并保存获取到的照片
     * @param context
     * @param requestCode
     * @param savePath
     */
    public static void openCamera(Context context,int requestCode,String savePath){
        if(!(context instanceof Activity)){
            return;
        }
        if(!PermissionUtil.checkPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            PermissionUtil.requestPermissions((Activity) context,110,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(context.getPackageManager())!=null){
                File file=new File(savePath);
                if(!file.getParentFile().exists()){
                    file.mkdirs();
                }
                /*获取当前系统的android版本号*/
                if (android.os.Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult((Activity)context,intent,requestCode);
                    ((Activity)context).startActivityForResult(intent, requestCode);
                }else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                    Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//这里加入flag
                    startActivityForResult((Activity)context,intent,requestCode);
                }
            }
        }


//        case CAMERA_REQUEST_CODE: //照相后返回
//        if (hasSdcard()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                Uri inputUri = FileProvider.getUriForFile(getActivity(), "com.renwohua.conch.fileprovider", mCameraFile);//通过FileProvider创建一个content类型的Uri
//                startPhotoZoom(inputUri);//设置输入类型
//            } else {
//                Uri inputUri = Uri.fromFile(mCameraFile);
//                startPhotoZoom(inputUri);
//            }
//        } else {
//            showToast("未找到存储卡，无法存储照片！");
//        }
//        break;
    }

    /**
     * 打开相册
     */
    public static void openAlbum(Activity activity,int requestCode) {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(activity,albumIntent, requestCode);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param inputUri
     */
    public void startPhotoZoom(Context context,Uri inputUri,File mCropFile,int requestCode) {
        if (!(context instanceof Activity)||inputUri == null) {
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri outPutUri = Uri.fromFile(mCropFile);
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        } else {
            Uri outPutUri = Uri.fromFile(mCropFile);
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                String url = GetImagePath.getPath(context, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(inputUri, "image/*");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        startActivityForResult((Activity) context,intent, requestCode);//这里就将裁剪后的图片的Uri返回了
    }


    /**
     *安装app，兼容安卓7.0安全文件的管理
     */
    public static void openApkFile(File var0, Context var1) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>=24){
            Uri uriForFile = FileProvider.getUriForFile(var1, var1.getApplicationContext().getPackageName() + ".provider", var0);
            intent.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            intent.setDataAndType(Uri.fromFile(var0), getMIMEType(var0));
        }
        try {
            var1.startActivity(intent);
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
