package east2d.com.download.listener;

/**
 * Created by leo on 2017/6/9.
 */

public interface TaskDataOption {

    // boolean isCanceled(String url,String savePath);
    String generalFileName(String url);
    long getMax(String url, String savePath);
    long getAvailable(String url, String savePath);
}
