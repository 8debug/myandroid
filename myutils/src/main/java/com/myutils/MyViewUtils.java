package com.myutils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.myutils.throwables.ToastException;

import java.util.ArrayList;
import java.util.List;

public class MyViewUtils {

    private static Context getContext(){
        return MyApplication.getContext();
    }

    public static List<String> getCheckedBoxsText(View viewWapper, String... texts){
        List<CheckBox> list = findManyWithText(viewWapper, texts);
        List<String> result = new ArrayList<>();
        for (CheckBox checkBox : list) {
            if( checkBox.isChecked() ){
                result.add(checkBox.getText().toString());
            }
        }
        return result;
    }

    public static <T extends View> List<T> findManyWithText(View viewWapper, String... texts){
        List<T> result = new ArrayList<>();
        for (String text : texts) {
            ArrayList<View> data = new ArrayList<>();
            viewWapper.findViewsWithText(data, text, View.FIND_VIEWS_WITH_TEXT);
            for (View view : data) {
                result.add((T)view);
            }
        }
        return result;
    }

    public static <T extends View> T findOneWithText(View viewWapper, String... texts){
        ArrayList<View> data = new ArrayList<>();
        for (String text : texts) {
            viewWapper.findViewsWithText(data, text, View.FIND_VIEWS_WITH_TEXT);
        }
        return (T)data.get(0);
    }

    public static void showDialogMenu(String title, String[] items, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setItems(items, onClickListener);
        builder.show();
    }

    public static ProgressDialog showLoadingDialog() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
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

    public static AlertDialog getSimpleAlert(String msg ){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setNegativeButton("确定", null);
        return builder.create();
    }

    public static void showSimpleAlert(String msg ){
        getSimpleAlert(msg).show();
    }

    public static void showException(Throwable throwable ){
        if( ToastException.class.isAssignableFrom(throwable.getClass()) ){
            showToask(throwable.getMessage());
        }else{
            getSimpleAlert(throwable.getMessage()).show();
        }
    }

    public static void showToask(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

}
