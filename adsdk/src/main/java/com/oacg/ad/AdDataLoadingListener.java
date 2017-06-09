package com.oacg.ad;

import java.util.List;

/**
 * 数据加载的监听
 * Created by leo on 2017/4/25.
 */

public interface AdDataLoadingListener<T> {
    void onError(int code, Object error);
    void onComplete(List<T> list, int space);
}
