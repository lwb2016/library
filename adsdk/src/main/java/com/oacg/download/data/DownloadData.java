package com.oacg.download.data;

/**
 * Created by leo on 2017/4/26.
 */

public class DownloadData {
    private Long db_id;
    private String url;
    private String save_path;
    private Long down_length;
    private Long all_length;
    private Integer status;

    public Long getDb_id() {
        return db_id;
    }

    public void setDb_id(Long db_id) {
        this.db_id = db_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSave_path() {
        return save_path;
    }

    public void setSave_path(String save_path) {
        this.save_path = save_path;
    }

    public Long getDown_length() {
        return down_length;
    }

    public void setDown_length(Long down_length) {
        this.down_length = down_length;
    }

    public Long getAll_length() {
        return all_length;
    }

    public void setAll_length(Long all_length) {
        this.all_length = all_length;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
