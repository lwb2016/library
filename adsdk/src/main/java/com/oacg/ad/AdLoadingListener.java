package com.oacg.ad;

/**
 * Created by leo on 2017/4/25.
 */

public interface AdLoadingListener {
    void onStart();
    void onError();
    void onComplete();
}
