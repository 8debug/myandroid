package com.myutils.throwables;

/**
 * activity级别的异常，出现此异常通过dialog提示，在点击确认后关闭activity
 */
public class ActivityException extends Exception {

    public ActivityException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    public ActivityException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
