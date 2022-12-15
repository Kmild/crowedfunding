package com.xiaoyan.crowd.exception;

public class CrowdRemoveInvalidException extends RuntimeException {

    private static final long serialVersionUID = 3L;

    public CrowdRemoveInvalidException() {
    }

    public CrowdRemoveInvalidException(String message) {
        super(message);
    }

    public CrowdRemoveInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrowdRemoveInvalidException(Throwable cause) {
        super(cause);
    }

    public CrowdRemoveInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
