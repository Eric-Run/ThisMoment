package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.Bean.Diary;
import com.dgut.moment.R;
import com.dgut.moment.Util.DatePickerUtil;
import com.dgut.moment.Util.ViewCenterUtils;

import java.lang.reflect.Field;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class DiaryDetailFragment extends Fragment {
    private String mTitle;
    private EditText TitleEt;
    private EditText ContentEt;
    private Button MoodBtn;
    private Button WeatherBtn;
    private TextView DateTv;
    private TextView MoodTv;
    private TextView WeatherTv;
    private Button ModifyBtn;
    private Button CancelBtn;
    private Button SaveBtn;
    private LinearLayout ModifyLayout;
    private LinearLayout SelectLayout;

    private Diary diary;


    public static DiaryDetailFragment getInstance(String title) {
        DiaryDetailFragment sf = new DiaryDetailFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_diary_check_detail, null);

        TitleEt = v.findViewById(R.id.diary_detail_title);
        ContentEt = v.findViewById(R.id.diary_detail_content);
        MoodBtn = v.findViewById(R.id.diary_detail_moodBtn);
        WeatherBtn = v.findViewById(R.id.diary_detail_weatherBtn);
        DateTv = v.findViewById(R.id.diary_detail_date);
        MoodTv = v.findViewById(R.id.diary_detail_mood);
        WeatherTv = v.findViewById(R.id.diary_detail_weather);
        ModifyBtn = v.findViewById(R.id.diary_detail_modify);
        CancelBtn = v.findViewById(R.id.diary_detail_cancel);
        SaveBtn = v.findViewById(R.id.diary_detail_save);
        ModifyLayout = v.findViewById(R.id.diary_modify_layout);
        SelectLayout = v.findViewById(R.id.diary_select_layout);

        // 实例化一个Bundle
        Bundle bundle=getArguments();
        //获取里面的Diary里面的数据
        diary = (Diary) bundle.getSerializable("diary") ;

        initView();  //初始化数据
        setAble(false);//设置不可点击

        setBtnListen(); //监听按钮




        return v;
    }

    //初始化详情页数据
    private void initView(){
        TitleEt.setText(diary.getTitle());
        ContentEt.setText(diary.getContent());
        int moodId = getContext().getResources().getIdentifier(diary.getMood(), "mipmap", getContext().getPackageName());
        MoodBtn.setBackgroundResource(moodId);
        MoodTv.setText(diary.getMood());
        int weatherId = getContext().getResources().getIdentifier(diary.getWeather(), "mipmap", getContext().getPackageName());
        WeatherBtn.setBackgroundResource(weatherId);
        WeatherTv.setText(diary.getWeather());
        DateTv.setText(diary.getDate());
    }

    //设置控件点击状态
    private void setAble(boolean status){
        TitleEt.setEnabled(status);
        ContentEt.setEnabled(status);
        WeatherBtn.setEnabled(status);
        MoodBtn.setEnabled(status);
        DateTv.setClickable(status);
    }

    //设置按钮监听
    private void setBtnListen(){

        //修改按钮
        ModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Diary_Detail","Modification started");
                //设置控件可点击
                setAble(true);
                TitleEt.setFocusable(true);
                TitleEt.setFocusableInTouchMode(true);
                TitleEt.requestFocus();
                //设置隐藏、显示按钮
                ModifyLayout.setVisibility(View.GONE);
                SelectLayout.setVisibility(View.VISIBLE);

            }
        });

        //取消修改按钮
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Diary_Detail","Modification canceled");
                initView();
                setAble(false);
                TitleEt.setFocusable(false);
                TitleEt.setFocusableInTouchMode(false);
                TitleEt.clearFocus();
                ContentEt.clearFocus();
                //设置隐藏、显示按钮
                ModifyLayout.setVisibility(View.VISIBLE);
                SelectLayout.setVisibility(View.GONE);

            }
        });

        //保存修改按钮
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Diary_Detail","Modification finished");
                //保存数据
                String text = "Title:"+TitleEt.getText().toString()+"\n"
                        +"Content:"+ContentEt.getText().toString()+"\n"
                        +"Mood:"+MoodTv.getText().toString()+"\n"
                        +"Weather"+WeatherTv.getText().toString()+"\n"
                        +"Date:"+DateTv.getText().toString();
                Log.d("Diary_Detail",text);
                //设置控件不可点击
                setAble(false);
                TitleEt.setFocusable(false);
                TitleEt.setFocusableInTouchMode(false);
                TitleEt.clearFocus();
                ContentEt.clearFocus();
                //设置隐藏、显示按钮
                ModifyLayout.setVisibility(View.VISIBLE);
                SelectLayout.setVisibility(View.GONE);

            }
        });

        //设置心情按钮
        MoodBtn.setOnClickListener(new View.OnClickListener() {
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
                                MoodBtn.setBackgroundResource(R.mipmap.happy);
                                MoodTv.setText("happy");
//                                Toast.makeText(getContext(),"Happy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Detail","pick mood ====>"+MoodTv.getText().toString());
                                break;
                            case R.id.menu_scared:
                                MoodBtn.setBackgroundResource(R.mipmap.scared);
                                MoodTv.setText("scared");
//                                Toast.makeText(getContext(),"Scared!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Detail","pick mood ====>"+MoodTv.getText().toString());
                                break;
                            case R.id.menu_notbad:
                                MoodBtn.setBackgroundResource(R.mipmap.notbad);
                                MoodTv.setText("notbad");
//                                Toast.makeText(getContext(),"Notbad!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Detail","pick mood ====>"+MoodTv.getText().toString());
                                break;
                            case R.id.menu_upset:
                                MoodBtn.setBackgroundResource(R.mipmap.upset);
                                MoodTv.setText("upset");
//                                Toast.makeText(getContext(),"Upset!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Detail","pick mood ====>"+MoodTv.getText().toString());
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

        //设置天气按钮
        WeatherBtn.setOnClickListener(new View.OnClickListener() {
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
                                WeatherBtn.setBackgroundResource(R.mipmap.sunny);
                                WeatherTv.setText("sunny");
//                                Toast.makeText(getContext(),"Sunny!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Detail","pick weather ====>"+WeatherTv.getText().toString());
                                break;
                            case R.id.menu_cloudy:
                                WeatherBtn.setBackgroundResource(R.mipmap.cloudy);
                                WeatherTv.setText("cloudy");
//                                Toast.makeText(getContext(),"Cloudy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Detail","pick weather ====>"+WeatherTv.getText().toString());
                                break;
                            case R.id.menu_rainy:
                                WeatherBtn.setBackgroundResource(R.mipmap.rainy);
                                WeatherTv.setText("rainy");
//                                Toast.makeText(getContext(),"Rainy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Detail","pick weather ====>"+WeatherTv.getText().toString());
                                break;
                            case R.id.menu_snowy:
                                WeatherBtn.setBackgroundResource(R.mipmap.snowy);
                                WeatherTv.setText("snowy");
//                                Toast.makeText(getContext(),"Snowy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Detail","pick weather ====>"+WeatherTv.getText().toString());
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

        //设置日期
        DateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerUtil().showDatePickDlg(DateTv,getContext());
            }
        });
    }
}
