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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.Bean.Diary;
import com.dgut.moment.Bean.User;
import com.dgut.moment.R;
import com.dgut.moment.Util.DatePickerUtil;
import com.dgut.moment.Util.ToastUtil;

import org.litepal.LitePal;

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
    private EditText addTitle;
    private EditText addContent;
    private Button moodBtn;
    private Button weatherBtn;
    private Button saveBtn;

    private SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
    private Date curDate =  new Date(System.currentTimeMillis());
    private OnDiarySaveListener diarySaveListener;

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
        addTitle = v.findViewById(R.id.diary_add_title);
        addContent = v.findViewById(R.id.diary_add_content);
        weatherBtn = v.findViewById(R.id.diary_add_weatherBtn);
        saveBtn = v.findViewById(R.id.diary_add_save);

        setBtnListen();
        initAddDate();
        return v;
    }

    //设置初始日期，监听选择日期
    private void initAddDate(){
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
//                                Toast.makeText(getContext(),"Happy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick mood ====>"+addMood.getText().toString());
                                break;
                            case R.id.menu_scared:
                                moodBtn.setBackgroundResource(R.mipmap.scared);
                                addMood.setText("scared");
//                                Toast.makeText(getContext(),"Scared!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick mood ====>"+addMood.getText().toString());
                                break;
                            case R.id.menu_notbad:
                                moodBtn.setBackgroundResource(R.mipmap.notbad);
                                addMood.setText("notbad");
//                                Toast.makeText(getContext(),"Notbad!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick mood ====>"+addMood.getText().toString());
                                break;
                            case R.id.menu_upset:
                                moodBtn.setBackgroundResource(R.mipmap.upset);
                                addMood.setText("upset");
//                                Toast.makeText(getContext(),"Upset!!!",Toast.LENGTH_SHORT).show();
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
//                                Toast.makeText(getContext(),"Sunny!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick weather ====>"+addWeather.getText().toString());
                                break;
                            case R.id.menu_cloudy:
                                weatherBtn.setBackgroundResource(R.mipmap.cloudy);
                                addWeather.setText("cloudy");
//                                Toast.makeText(getContext(),"Cloudy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick weather ====>"+addWeather.getText().toString());
                                break;
                            case R.id.menu_rainy:
                                weatherBtn.setBackgroundResource(R.mipmap.rainy);
                                addWeather.setText("rainy");
//                                Toast.makeText(getContext(),"Rainy!!!",Toast.LENGTH_SHORT).show();
                                Log.d("Diary_Add","pick weather ====>"+addWeather.getText().toString());
                                break;
                            case R.id.menu_snowy:
                                weatherBtn.setBackgroundResource(R.mipmap.snowy);
                                addWeather.setText("snowy");
//                                Toast.makeText(getContext(),"Snowy!!!",Toast.LENGTH_SHORT).show();
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

        //监听保存按钮
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //添加数据库数据
                if("".equals(addTitle.getText().toString())){
                    ToastUtil.ToastCenter(getContext(),"标题不能为空");
                }else if("".equals(addContent.getText().toString())){
                    ToastUtil.ToastCenter(getContext(),"内容不能为空");
                }else {
                    Diary diary = new Diary();
                    diary.setTitle(addTitle.getText().toString());
                    diary.setContent(addContent.getText().toString());
                    diary.setDate(addDate.getText().toString());
                    diary.setMood(addMood.getText().toString());
                    diary.setWeather(addWeather.getText().toString());
                    Log.d("Diary_Add", diary.toString());
                    diary.save();

                    addTitle.setText("");
                    addContent.setText("");
                    addDate.setText(formatter.format(curDate));

//                diarySaveListener.diarySaved();
                    ToastUtil.ToastCenter(getContext(), "成功记下一篇日记");

                    User user = LitePal.find(User.class,1);
                    user.setDiarycount(user.getDiarycount()+1);
                    user.save();
                    Log.d("Diary_Add","用户："+user.getUsername()+"增加了一篇日记");
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        diarySaveListener = (OnDiarySaveListener) getActivity();
    }

    public interface OnDiarySaveListener{
        public void diarySaved();
    }

    public void setOnDiarySaveListener(OnDiarySaveListener diarySaveListener){
        this.diarySaveListener = diarySaveListener;
    }
}
