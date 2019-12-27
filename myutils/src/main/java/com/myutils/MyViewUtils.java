package com.myutils;

import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class MyViewUtils {

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

}
