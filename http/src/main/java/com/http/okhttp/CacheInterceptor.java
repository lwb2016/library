package com.http.okhttp;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptors 拦截器
 * Created by leo on 2017/1/4.
 */
public class CacheInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isNetworkConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
//        1.不需要缓存：Cache-Control: no-cache或Cache-Control: max-age=0
//
//        2.如果想先显示数据，在请求。（类似于微博等）：Cache-Control: only-if-cached

        if (isNetworkConnected()) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置(注掉部分)
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .header("Cache-Control", "max-age="+getMaxAge())
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            int maxAge = getMaxAge();
            return originalResponse.newBuilder()
                    //.header("Cache-Control", "public, only-if-cached, max-age=" + maxAge)
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    }

    protected boolean isNetworkConnected() {
        return true;
    }

    protected int getMaxAge(){
        return 10;
    }
}
