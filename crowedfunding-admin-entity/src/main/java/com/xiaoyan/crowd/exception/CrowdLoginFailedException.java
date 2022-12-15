package com.xiaoyan.crowd.exception;

public class CrowdLoginFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CrowdLoginFailedException() {
    }

    public CrowdLoginFailedException(String message) {
        super(message);
    }

    public CrowdLoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrowdLoginFailedException(Throwable cause) {
        super(cause);
    }

    public CrowdLoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
