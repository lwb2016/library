package east2d.com.myapplication.dowload;

/**
 * Created by leo on 2017/4/18.
 */

public interface OnManageDownloadListener {
    void onDownloadCancel(String url);
    void onDownloadPause(String url);
    void onDownloadError(String url,Object error);
    void onDownloadComplete(String url,String path);
}
