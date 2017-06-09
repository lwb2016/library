package east2d.com.download.exception;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * Created by leo on 2017/5/12.
 */

public class DownloadException extends RuntimeException{
    public DownloadException() {
    }

    public DownloadException(String message) {
        super(message);
    }

    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadException(Throwable cause) {
        super(cause);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public DownloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
