package com.http.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.libbase.tool.LogU;

/**
 * 封装Okhttp3的请求类
 * Created by KingWin on 2016/10/21.
 */
public class OkHttpRequest {

    private final String TAG = "OkHttpRequest";

    private static OkHttpClient sOkHttpClient;

    public static OkHttpClient getDefaultHttpClient() {
        synchronized (OkHttpRequest.class) {
            if (sOkHttpClient == null) {
                synchronized (OkHttpRequest.class) {
                    if (sOkHttpClient == null) {
                        sOkHttpClient = new OkHttpClient.Builder()
                                .addInterceptor(new CacheInterceptor())
                                .addNetworkInterceptor(new CacheInterceptor())
                                //作相应的优化处理
                                .build();
                    }
                }
            }
        }
        return sOkHttpClient;
    }

    private OkHttpClient mOkHttpClient;

    public OkHttpClient getHttpClient(){
        if(mOkHttpClient==null){
            mOkHttpClient=getDefaultHttpClient();
        }
        return mOkHttpClient;
    }

    public void setHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    public static final String POST = "post";
    public static final String PUT = "put";
    public static final String GET = "get";
    public static final String DELETE = "delete";
    public static final String PATCH = "patch";
    public static final String HEAD = "head";

    ////////////////////////////////////////////////同步请求/////////////////////////////////

    /**
     * 不带header
     *
     * @param url         请求地址
     * @param requestBody 内容封装的内容，get
     * @param method      暂支持 get post put，其他方式均通过get进行请求
     * @return
     */
    public RequestBackCode syncReqHttpData(String url, RequestBody requestBody, String method) {
        return syncReqHttpData(url, null, requestBody, method);
    }


    /**
     * 带header
     *
     * @param url
     * @param headers
     * @param requestBody
     * @param method
     * @return
     */
    public RequestBackCode syncReqHttpData(String url, Headers headers, RequestBody requestBody, String method) {
        Call call = null;
        try {
            LogU.i(TAG, url);
            //创建一个Request
            final Request request = getBuilder(url, headers, requestBody, method).build();
            call = getHttpClient().newCall(request);
            //请求加入调度
            return getAnalysisRequestResult(syncCall(call));
        } catch (Exception e) {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
            LogU.i(TAG, "IOException_req:" + e.getMessage());
            return new RequestBackCode(RequestBackCode.OTHER_ERROR, e.getMessage());
        }
    }

    /**
     * 创建获取类
     *
     * @param url
     * @param headers
     * @param requestBody
     * @param method
     * @return
     */
    public Request.Builder getBuilder(String url, Headers headers, RequestBody requestBody, String method) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (method.equals(POST)) {
            builder.post(requestBody);
        } else if (method.equals(PUT)) {
            builder.put(requestBody);
        } else if (method.equals(GET)) {
            builder.get();
        } else if (method.equals(DELETE)) {
            if (requestBody == null) {
                builder.delete();
            } else {
                builder.delete(requestBody);
            }
        } else if (method.equals(PATCH)) {
            builder.patch(requestBody);
        } else if (method.equals(HEAD)) {
            builder.head();
        }
        if (headers != null) {
            builder.headers(headers);
        }
        return builder;
    }

    ////////////////////////////////////////////////异步请求/////////////////////////////////


    /**
     * 不带header
     *
     * @param url
     * @param requestBody
     * @param method
     * @param callBack
     */
    public void asyncReqHttpData(String url, RequestBody requestBody, String method, IRequestCallBack callBack) {
        asyncReqHttpData(url, null, requestBody, method, callBack);
    }


    /**
     * 带header
     *
     * @param url
     * @param requestBody
     * @param method
     * @param callBack
     */
    public void asyncReqHttpData(String url, Headers headers, RequestBody requestBody, String method, IRequestCallBack callBack) {
        Call call = null;
        try {
            LogU.i(TAG, url);
            //创建一个Request
            final Request request = getBuilder(url, headers, requestBody, method).build();
            call = getHttpClient().newCall(request);
            //请求加入调度
            asyncCall(call, callBack);
        } catch (Exception e) {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
            LogU.i(TAG, "IOException_req:" + e.getMessage());
            if (callBack != null) {
                callBack.onError(RequestBackCode.OTHER_ERROR, e.getMessage());
            }
        }
    }


    //////////////////////////////////////请求方式与请求结果分析/////////////////////////////////

    /**
     * 异步请求
     *
     * @param call
     */
    private void asyncCall(Call call, final IRequestCallBack callBack) {
        //请求加入调度
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                analysisRequestResult(response, callBack);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
                LogU.i(TAG, "IOException_async:" + e.getMessage());
                if (callBack != null) {
                    callBack.onError(RequestBackCode.OTHER_ERROR, e.getMessage());
                }
            }
        });
    }

    /**
     * 同步请求
     *
     * @param call
     */
    private Response syncCall(Call call) {
        try {
            //请求加入调度
            return call.execute();
        } catch (IOException e) {
            if (!call.isCanceled()) {
                call.cancel();
            }
            LogU.i(TAG, "IOException_sync:" + e.getMessage());
            return null;
        }
    }


//////////////////////////////////////////结果分析////////////////////////////////////////////////

    /**
     * 请求结果分析(异步)
     *
     * @param response
     */
    private void analysisRequestResult(Response response, IRequestCallBack callBack) {
        RequestBackCode codeBackCode = getAnalysisRequestResult(response);
        if (codeBackCode != null && codeBackCode.getCode() == RequestBackCode.SERVER_CONNECT_OK) {
            if (callBack != null) {
                callBack.onOK(codeBackCode.getCode(), codeBackCode.getResult());
            }
        } else {
            if (callBack != null) {
                callBack.onError(codeBackCode.getCode(), codeBackCode.getResult());
            }
        }
    }


    /**
     * 请求结果分析（同步）
     *
     * @param response
     */
    private RequestBackCode getAnalysisRequestResult(Response response) {
        RequestBackCode backCode = new RequestBackCode();
        try {
            if (response == null) {
                backCode.setCode(RequestBackCode.SERVER_CONNECT_FAIL);
                backCode.setResult("response==null");
                LogU.i(TAG, "response==null");
            } else if (response.isSuccessful()) { //200-300
                backCode.setCode(RequestBackCode.SERVER_CONNECT_OK);
                String result = response.body().string();
                backCode.setResult(result);
                LogU.i(TAG, "SERVER_CONNECT_OK:" + result);
            } else if (response.code() == 401) { //未授权
                backCode.setCode(RequestBackCode.NO_UNAUTHORIZED);
                String result = response.body().string();
                backCode.setResult(result);
                LogU.i(TAG, "NO_UNAUTHORIZED" + result);
            } else if (response.code() >= 500) {   //服务器错误
                backCode.setCode(RequestBackCode.SERVER_ERROR);
                String result = response.body().string();
                backCode.setResult(result);
                LogU.i(TAG, "SERVER_ERROR:" + result);
            } else {
                backCode.setCode(RequestBackCode.OTHER_ERROR);
                String result = response.body().string();
                backCode.setResult(result);
                LogU.i(TAG, "OTHER_ERROR" + result);
            }
        } catch (IOException e) {
            backCode.setCode(RequestBackCode.OTHER_ERROR);
            backCode.setResult(e.getMessage());
            LogU.i(TAG, "IOException" + e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return backCode;
    }
}
