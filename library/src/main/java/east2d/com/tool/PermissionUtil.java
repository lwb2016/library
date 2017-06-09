package east2d.com.tool;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 基于安卓的权限请求类
 * Created by leo on 2017/2/15.
 */

public class PermissionUtil {

    /**
     * 检查权限是否已经全部授权
     * 仅针对于安卓6.0及以上系统，安卓6.0以下直接申明就可
     *
     * @param permissions
     */
    public static boolean checkPermissions(Context context, String... permissions) {
        if (permissions == null || permissions.length == 0)
            return true;
        //>=23(安卓6.0及以上)需要检查权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                int result = ContextCompat.checkSelfPermission(context, permission);
                if (result == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 请求权限（必须在activity中进行请求）
     *
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

}
