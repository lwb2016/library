package east2d.com.ui.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by leo on 2017/4/25.
 */

public class BaseUiHandler {
    //开启和关闭Activity

    public static void startActivity(Context context, Class<?> clazz){
        Intent intent=new Intent(context,clazz);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Class<?> classname, int requestCode) {
        Intent t = new Intent(activity,classname);
        activity.startActivityForResult(t,requestCode);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent,requestCode);
    }
}
