package com.oacg.ad;

/**
 * AD广告异常
 * Created by leo on 2017/4/25.
 */

public class AdException extends RuntimeException {

    public AdException() {
        super();
    }

    public AdException(String message) {
        super(message);
    }

    public AdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdException(Throwable cause) {
        super(cause);
    }
}
