package east2d.com.download.manager;

/**
 * Created by leo on 2017/4/26.
 */

public interface IUnitDownloadOption<T> {

    /**
     * 添加新的下载任务
     * @param url
     */
    T get(String url);


    /**
     * 添加新的下载任务
     * @param url
     */
    boolean add(String url, String path);

    /**
     * 删除下载的任务（包含下载记录）
     * @param url
     */
    boolean delete(String url);

    /**
     * 暂停下载的任务
     * @param url
     */
    boolean pause(String url);

    /**
     * 取消下载的任务（不删除下载的记录）
     * @param url
     */
    boolean cancel(String url);

    /**
     * 检查任务是否存在
     * @param url
     * @return
     */
    boolean check(String url);

    /**
     * 获取任务的状态
     * @param url
     * @return
     */
    int getStatus(String url);

    /**
     * 获取任务的状态
     * @param url
     * @return
     */
    void setProgress(String url,long current,long max);

}
