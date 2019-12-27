package com.myutils.throwables;

public class AlertException extends Exception {

    public AlertException(String msg){
        super(msg);
    }

    public AlertException(Throwable throwable){
        super(throwable);
    }

    public AlertException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
