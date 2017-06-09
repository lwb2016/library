package east2d.com.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by leo on 2017/6/9.
 */

public class UriUtil {
    private static final String APPLICATION_ID="com.oacg";


    public static Uri getUriByFile(Context context,File file){
        Uri uri=null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            uri= FileProvider.getUriForFile(context,APPLICATION_ID+".fileprovider",file);
        }else{
            uri=Uri.fromFile(file);
        }
        return uri;
    }

    /**安卓7.0裁剪根据文件路径获取uri*/
    public static Uri getImageContentUri(Context context, File imageFile) {
        Uri uri=null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String filePath = imageFile.getAbsolutePath();
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[] { MediaStore.Images.Media._ID },
                    MediaStore.Images.Media.DATA + "=? ",
                    new String[] { filePath }, null);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                return Uri.withAppendedPath(baseUri, "" + id);
            } else {
                if (imageFile.exists()) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.DATA, filePath);
                    return context.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                } else {
                    return null;
                }
            }
        }else{
            uri=Uri.fromFile(imageFile);
        }
        return uri;
    }
}
