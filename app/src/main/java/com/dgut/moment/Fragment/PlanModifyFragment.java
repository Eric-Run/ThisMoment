package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.R;
import com.dgut.moment.Util.DatePickerUtil;

import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class PlanModifyFragment extends Fragment {
    private String mTitle;
    private Switch reminder;
    private LinearLayout reminderLayout;
    private LinearLayout planTimeLo;
    private LinearLayout btnLayout;
    private TextView TitleTv;
    private TextView plantimeTv;

    public static PlanModifyFragment getInstance(String title) {
        PlanModifyFragment sf = new PlanModifyFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_plan_detail, null);

        reminder = v.findViewById(R.id.plan_remind);
        reminderLayout = v.findViewById(R.id.plan_remind_layout);
        planTimeLo = v.findViewById(R.id.plan_time_layout);
        btnLayout = v.findViewById(R.id.plan_btn_layout);
        TitleTv = v.findViewById(R.id.plan_title);
        plantimeTv = v.findViewById(R.id.plan_time);

        initView();  //初始化界面

        setListener();  //设置监听器


        return v;
    }

    //初始化界面
    private void initView(){

        TitleTv.setText("修改计划");

    }

    //设置监听器
    private void setListener(){

        //防止点击时switch异常变化
        btnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //日期时间选择器
        planTimeLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerUtil().showTimePickView(plantimeTv,getContext());


                Toast.makeText(getActivity(),"Pick Time",Toast.LENGTH_SHORT).show();
            }
        });

        //提示时间选择
        reminderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Pick Remind Time",Toast.LENGTH_SHORT).show();

            }
        });

        //是否开启提醒
        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isPressed()) {
                    if (isChecked) {
                        reminderLayout.setVisibility(View.VISIBLE);
                    } else {
                        reminderLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}
