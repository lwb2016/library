package com.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leo on 2017/5/24.
 */

public class ApiRetrofit {

    private static final int DEFAULT_TIME_OUT=10;

    private static Retrofit sRetrofit;

    private static OkHttpClient sBaseHttpClient;

    public synchronized static Retrofit getRetrofit(){
        if(sRetrofit==null){
            synchronized (ApiRetrofit.class){
                if(sRetrofit==null){
                    sRetrofit=new Retrofit.Builder()
                            .baseUrl(HttpConstants.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(getBaseHttpClient())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    public synchronized static OkHttpClient getBaseHttpClient(){
        if(sBaseHttpClient==null){
            synchronized (ApiRetrofit.class){
                if(sBaseHttpClient==null){
                    sBaseHttpClient=new OkHttpClient.Builder()
                            .build();
                }
            }
        }
        return sBaseHttpClient;
    }

    public static <T>  T getService(Class<T> clazz){
        return getRetrofit().create(clazz);
    }

}
