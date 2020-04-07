package com.dgut.moment.Util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bigkoo.pickerview.TimePickerView;
import com.dgut.moment.Fragment.BillCheckFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerUtil {

    public DatePickerUtil() {
    }

    //显示日期选择器
    public void showDatePickDlg (final TextView textView, Context context) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month;
                int i = monthOfYear+1;
                if(i<10){

                    month = "0" + i;
                }else {
                    month = i + "";
                }
                textView.setText(year + "-" + month + "-" + dayOfMonth);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    //显示时间选择器
    public void showTimePickDlg(TextView textView, Context context){
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            }
        }, 1,1,true);
        timePickerDialog.show();
    }

    /**
     * 谷歌库    implementation 'com.contrarywind:Android-PickerView:3.2.7'
     * 显示日期时间选择器
     * @param textView
     * @param context
     */
    public void showTimePickView(final TextView textView, Context context){
        Calendar calendar = Calendar.getInstance();

        TimePickerView timePickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                textView.setText(getTimes(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(true)
                .setContentSize(21)
                .setDecorView(null)
                .build();
        timePickerView.show();
    }

    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return format.format(date);
    }

    //返回时间选择器的子控件（用于隐藏子控件）
    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }
}
