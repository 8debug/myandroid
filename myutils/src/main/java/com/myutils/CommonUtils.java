package com.myutils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;

import com.myutils.throwables.ToastException;

import java.util.regex.Pattern;

public class CommonUtils {

    public static boolean isMobileValid(String mobile) {
        return Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$").matcher(mobile).matches();
    }

    public static boolean isNotMobileValid(String mobile) {
        return !isMobileValid(mobile);
    }

    public static boolean isNumber(String str) {
        return Pattern.compile("-?[0-9]+(\\.[0-9]+)?").matcher(str).matches();
    }

    public static void showToask(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showDialogMenu(Context context, String title, String[] items, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, onClickListener);
        builder.show();
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static AlertDialog getSimpleAlert(Context context, String msg ){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setNegativeButton("确定", null);
        return builder.create();
    }

    public static void showSimpleAlert(Context context, String msg ){
        getSimpleAlert(context, msg).show();
    }

    public static void showException(Context context, Throwable throwable ){
        if( ToastException.class.isAssignableFrom(throwable.getClass()) ){
            showToask(context, throwable.getMessage());
        }else{
            getSimpleAlert(context, throwable.getMessage()).show();
        }
    }
}
