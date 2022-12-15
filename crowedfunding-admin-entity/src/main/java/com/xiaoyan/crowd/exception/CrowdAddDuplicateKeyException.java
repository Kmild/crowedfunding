package com.xiaoyan.crowd.exception;

public class CrowdAddDuplicateKeyException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public CrowdAddDuplicateKeyException() {
    }

    public CrowdAddDuplicateKeyException(String message) {
        super(message);
    }

    public CrowdAddDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrowdAddDuplicateKeyException(Throwable cause) {
        super(cause);
    }

    public CrowdAddDuplicateKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
