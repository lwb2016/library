package com.http.okhttp;

/**
 * Created by leo on 2016/12/8.
 */
public interface IRequestCallBack {
    void onOK(int code, String response);

    void onError(int code, String msg);
}
