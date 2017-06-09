package com.oacg.ad;

import com.oacg.networkrequest.CacheInterceptor;

import east2d.com.tool.NetworkUtils;

/**
 * 广告请求缓存拦截器
 * Created by leo on 2017/4/27.
 */

public class AdCacheInterceptor extends CacheInterceptor {
    @Override
    protected boolean isNetworkConnected() {
        return NetworkUtils.isConnected(AdSDK.get().getContext());
    }

    @Override
    protected int getMaxAge() {
        return 10;
    }
}
