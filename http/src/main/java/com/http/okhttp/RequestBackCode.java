package com.http.okhttp;

/**
 * Created by KingWin on 2016/9/22.
 */
public class RequestBackCode {

    ///////////////////////////////////客户端返还码////////////////////////////////
    /**
     * 解析数据  成功
     */
    public static final int LOADING_OK = 0x0001;

    /**
     * 解析数据  失败
     */
    public static final int LOADING_ERR = 0x0002;

    /**
     * 已加载全部
     */
    public static final int LOADING_OVER = 0x0003;

    /**
     * 当前无网
     */
    public static final int NONETWORK = 0x0004;

    /**
     * 未登录
     */
    public static final int NO_LOGIN = 0x0005;

    /**
     * 无需请求服务器
     */
    public static final int NO_NEED_REQUEST = 0x0010;


    ////////////////////////////////服务器端返还码///////////////////////////////////

    /**
     * 未授权(400-500)
     */
    public static final int NO_UNAUTHORIZED = 0x0006;

    /**
     * 服务器错误(500-)
     */
    public static final int SERVER_ERROR = 0x0007;

    /**
     * 服务器连接成功(200-300)
     */
    public static final int SERVER_CONNECT_OK = 0x0008;

    /**
     * 其他错误(else)
     */
    public static final int OTHER_ERROR = 0x0009;


    /**
     * 服务器连接失败(没连上)
     */
    public static final int SERVER_CONNECT_FAIL = 0x0011;


    /////////////////////////////////////返还码载体//////////////////////////////


    private int code = OTHER_ERROR;

    private String result;

    public RequestBackCode() {
    }

    public RequestBackCode(int code, String result) {
        this.code = code;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
