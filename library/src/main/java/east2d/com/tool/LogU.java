package east2d.com.tool;

import android.util.Log;

/**
 * log打印工具包（统一管理所有的打印）
 * Created by leo on 2017/2/23.
 */

public class LogU {

    public static final String HEADER = "Library:";
    public static final boolean LOG_ENABLE = true;

    public static void i(String tag, String content) {
        if (LOG_ENABLE) {
            Log.i(HEADER + tag, content);
        }
    }

    public static void v(String tag, String content) {
        if (LOG_ENABLE) {
            Log.v(HEADER + tag, content);
        }
    }

    public static void e(String tag, String content) {
        if (LOG_ENABLE) {
            Log.e(HEADER + tag, content);
        }
    }

    public static void d(String tag, String content) {
        if (LOG_ENABLE) {
            Log.d(HEADER + tag, content);
        }
    }

    public static void w(String tag, String content) {
        if (LOG_ENABLE) {
            Log.w(HEADER + tag, content);
        }
    }

}
