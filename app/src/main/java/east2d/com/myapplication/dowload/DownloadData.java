package east2d.com.myapplication.dowload;

/**
 * 下载的数据data
 * Created by leo on 2017/4/18.
 */

public class DownloadData {
    private Long DB_ID;
    private String download_path;
    private String save_path;
    private Long progress;
    private Long max;
    private Integer status;
    private String name;


    public Long getDB_ID() {
        return DB_ID;
    }

    public void setDB_ID(Long DB_ID) {
        this.DB_ID = DB_ID;
    }

    public String getDownload_path() {
        return download_path;
    }

    public void setDownload_path(String download_path) {
        this.download_path = download_path;
    }

    public String getSave_path() {
        return save_path;
    }

    public void setSave_path(String save_path) {
        this.save_path = save_path;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
