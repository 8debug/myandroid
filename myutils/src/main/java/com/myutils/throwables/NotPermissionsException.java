package com.myutils.throwables;

public class NotPermissionsException extends ToastException {

    public NotPermissionsException(){
        super("缺少相应权限");
    }

    public NotPermissionsException(String message){
        super(message);
    }
}
