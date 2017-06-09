package com.oacg.service;

import android.content.Context;

import east2d.com.tool.KEncryption;

/**
 * 下载文件服务
 * Created by leo on 2017/4/25.
 */

public class FileDownloadService extends DownLoadService {
    @Override
    protected void handlerFileDownloadComplete(Context context, String path) {

    }

    @Override
    protected String getDirPath() {
        return BASE_DIR+"file/";
    }

    @Override
    protected String getFileName(String url) {
        return KEncryption.md5(url)+url.substring(url.lastIndexOf("."));
    }
}
