package com.xiaoyan.crowd.exception;

public class CrowdForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public CrowdForbiddenException() {
    }

    public CrowdForbiddenException(String message) {
        super(message);
    }

    public CrowdForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrowdForbiddenException(Throwable cause) {
        super(cause);
    }

    public CrowdForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
