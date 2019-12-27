package com.myutils.throwables;

public class ToastException extends Exception {

    public ToastException(String msg) {
        super(msg);
    }

    public ToastException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    public ToastException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
