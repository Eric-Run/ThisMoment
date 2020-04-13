package com.dgut.moment.Util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {

    public static void ToastButtom(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public static void ToastCenter(Context context,String s){
        Toast toast = Toast.makeText(context,s,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
