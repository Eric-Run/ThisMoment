package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.R;
import com.dgut.moment.Util.DatePickerUtil;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class DiaryAddFragment extends Fragment {
    private String mTitle;
    private TextView addMood;
    private TextView addWeather;
    private TextView addDate;
    private Button moodBtn;
    private Button weatherBtn;

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
        moodBtn = v.findViewById(R.id.diary_add_moodBtn);
        weatherBtn = v.findViewById(R.id.diary_add_weatherBtn);

        setBtnListen();
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

    //设置心情、天气按钮监听
    private void setBtnListen(){
        moodBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.diary_mood_menu,popupMenu.getMenu());

                //弹出式菜单的菜单项点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.menu_happy:
                                moodBtn.setBackgroundResource(R.mipmap.happy);
                                addMood.setText("happy");
                                Toast.makeText(getContext(),"Happy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick mood ====>"+addMood.getText().toString());
                                break;
                            case R.id.menu_scared:
                                moodBtn.setBackgroundResource(R.mipmap.scared);
                                addMood.setText("scared");
                                Toast.makeText(getContext(),"Scared!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick mood ====>"+addMood.getText().toString());
                                break;
                            case R.id.menu_notbad:
                                moodBtn.setBackgroundResource(R.mipmap.notbad);
                                addMood.setText("notbad");
                                Toast.makeText(getContext(),"Notbad!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick mood ====>"+addMood.getText().toString());
                                break;
                            case R.id.menu_upset:
                                moodBtn.setBackgroundResource(R.mipmap.upset);
                                addMood.setText("upset");
                                Toast.makeText(getContext(),"Upset!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick mood ====>"+addMood.getText().toString());
                                break;

                        }
                        return false;
                    }
                });
                //显示popmenu的图标icon
                try {
                    Field field = popupMenu.getClass().getDeclaredField("mPopup");
                    field.setAccessible(true);
                    MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
                    mHelper.setForceShowIcon(true);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
                popupMenu.show();
            }
        });
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.diary_weather_menu,popupMenu.getMenu());

                //弹出式菜单的菜单项点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.menu_sunny:
                                weatherBtn.setBackgroundResource(R.mipmap.sunny);
                                addWeather.setText("sunny");
                                Toast.makeText(getContext(),"Sunny!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick weather ====>"+addWeather.getText().toString());
                                break;
                            case R.id.menu_cloudy:
                                weatherBtn.setBackgroundResource(R.mipmap.cloudy);
                                addWeather.setText("cloudy");
                                Toast.makeText(getContext(),"Cloudy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick weather ====>"+addWeather.getText().toString());
                                break;
                            case R.id.menu_rainy:
                                weatherBtn.setBackgroundResource(R.mipmap.rainy);
                                addWeather.setText("rainy");
                                Toast.makeText(getContext(),"Rainy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick weather ====>"+addWeather.getText().toString());
                                break;
                            case R.id.menu_snowy:
                                weatherBtn.setBackgroundResource(R.mipmap.snowy);
                                addWeather.setText("snowy");
                                Toast.makeText(getContext(),"Snowy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick weather ====>"+addWeather.getText().toString());
                                break;

                        }
                        return false;
                    }
                });
                //显示popmenu的图标icon
                try {
                    Field field = popupMenu.getClass().getDeclaredField("mPopup");
                    field.setAccessible(true);
                    MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
                    mHelper.setForceShowIcon(true);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
                popupMenu.show();
            }
        });
    }
}
