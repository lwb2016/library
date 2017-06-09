package east2d.com.download.listener;

/**
 * Created by leo on 2017/4/26.
 */

public interface DownloadListener {
    void onStart(String url, String savePath);
    void onProgress(String url, String savePath, long progress, long max);
    void onComplete(String url, String savePath);
    void onCancel(String url, String savePath);
    void onFail(String url, String savePath);
    void onPause(String url, String savePath);
}
