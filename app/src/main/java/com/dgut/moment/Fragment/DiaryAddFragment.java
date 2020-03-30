package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgut.moment.R;
import com.dgut.moment.Util.DatePickerUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class DiaryAddFragment extends Fragment {
    private String mTitle;
    private TextView addMood;
    private TextView addWeather;
    private TextView addDate;

    public static DiaryAddFragment getInstance(String title) {
        DiaryAddFragment sf = new DiaryAddFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_diary_add, null);

        addMood = v.findViewById(R.id.diary_add_mood);
        addWeather = v.findViewById(R.id.diary_add_weather);
        addDate = v.findViewById(R.id.diary_add_date);

        initAddDate();
        return v;
    }

    //设置初始日期，监听选择日期
    private void initAddDate(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
        addDate.setText(formatter.format(curDate));
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerUtil().showDatePickDlg(addDate,getContext());
            }
        });
    }
}
