package east2d.com.ui.handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;


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
}
