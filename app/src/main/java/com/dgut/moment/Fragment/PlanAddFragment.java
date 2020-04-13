package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.Bean.Plan;
import com.dgut.moment.PlanActivity;
import com.dgut.moment.R;
import com.dgut.moment.Util.CalendarReminderUtils;
import com.dgut.moment.Util.DatePickerUtil;
import com.dgut.moment.Util.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class PlanAddFragment extends Fragment {
    private String mTitle;
    private Switch reminder;
    private LinearLayout reminderLayout;
    private LinearLayout planTimeLo;
    private LinearLayout btnLayout;
    private TextView plantimeTv;
    private Button planBtn;
    private EditText contentEv;
    private TextView remindTimeTv;
    private int previousTime;

    public static PlanAddFragment getInstance(String title) {
        PlanAddFragment sf = new PlanAddFragment();
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
        plantimeTv = v.findViewById(R.id.plan_time);
        remindTimeTv = v.findViewById(R.id.plan_remind_time);
        planBtn = v.findViewById(R.id.plan_btn);
        contentEv = v.findViewById(R.id.plan_content);

        previousTime = 0;

        setListener();  //设置监听器

        return v;
    }

    //设置监听器
    private void setListener(){

        //防止点击时switch变化
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

        //选择提醒时间
        reminderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Pick Remind Time",Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.plan_remind_menu,popupMenu.getMenu());

                //弹出式菜单的菜单项点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.plan_time0:
                                remindTimeTv.setText("发生时");
                                break;
                            case R.id.plan_time1:
                                remindTimeTv.setText("提前5分钟");
                                previousTime = 5;
                                break;
                            case R.id.plan_time2:
                                remindTimeTv.setText("提前10分钟");
                                previousTime = 10;
                                break;
                            case R.id.plan_time3:
                                remindTimeTv.setText("提前30分钟");
                                previousTime = 30;
                                break;
                            case R.id.plan_time4:
                                remindTimeTv.setText("提前1小时");
                                previousTime = 60;
                                break;
                            case R.id.plan_time5:
                                remindTimeTv.setText("提前一天");
                                previousTime = 1 * 24 * 60;
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
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

        //监听保存按钮
        planBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String planTime = plantimeTv.getText().toString();
                String remindeTime = remindTimeTv.getText().toString();
                String content = contentEv.getText().toString();
                /*
                1、判断计划内容是否为空（->否，返回提醒）
                2、判断计划时间是否已选（->否，返回提醒）
                3、判断是否有设置提醒（->是，判断提醒时间是否已选->否，返回提醒）
                4、保存计划数据
                 */
                if("".equals(content)|content == null) {

                    Toast.makeText(getActivity(), "计划内容不能为空", Toast.LENGTH_SHORT).show();
                }else if ("请选择".equals(planTime)) {
                    Toast.makeText(getActivity(), "请选择计划时间", Toast.LENGTH_SHORT).show();
                }else {
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        Date planDate = sdf.parse(planTime);

                        if(reminder.isChecked()){
                            if("请选择".equals(remindeTime)){
                                Toast.makeText(getActivity(), "请设置有效提醒时间", Toast.LENGTH_SHORT).show();
                            }else {
                                //添加系统日历事件
                                CalendarReminderUtils.addCalendarEvent(getContext(), content, planDate.getTime(), previousTime);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //保存计划数据
                    Plan plan = new Plan();
                    plan.setContent(content);
                    plan.setIsfinished(0);
                    plan.setPlantime(planTime);
                    plan.save();
                    Log.d("Plan_",plan.toString());
                    ToastUtil.ToastCenter(getContext(),"已开启一项计划");

                }

            }
        });

    }

}
