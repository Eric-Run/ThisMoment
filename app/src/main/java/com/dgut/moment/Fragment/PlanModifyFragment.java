package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

@SuppressLint("ValidFragment")
public class PlanModifyFragment extends Fragment {
    private String mTitle;
    private Switch reminder;
    private LinearLayout reminderLayout;
    private LinearLayout planTimeLo;
    private LinearLayout btnLayout;
    private TextView TitleTv;
    private TextView plantimeTv;
    private Button planBtn;
    private EditText contentEv;
    private TextView remindTimeTv;
    private int previousTime;
    private int pt;

    private Plan plan;
    private PlanModifyListener planModifyListener;

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
        remindTimeTv = v.findViewById(R.id.plan_remind_time);
        planBtn = v.findViewById(R.id.plan_btn);
        contentEv = v.findViewById(R.id.plan_content);

        // 实例化一个Bundle
        Bundle bundle=getArguments();
        //获取里面的Diary里面的数据
        plan = (Plan) bundle.getSerializable("plan") ;


        initView();  //初始化界面

        setListener();  //设置监听器


        return v;
    }

    //初始化界面
    private void initView(){

        TitleTv.setText("修改计划");
        contentEv.setText(plan.getContent());
        plantimeTv.setText(plan.getPlantime());
        previousTime = plan.getPreviousTime();
        if(plan.getIsremind()==1) {
            reminder.setChecked(true);
            reminderLayout.setVisibility(View.VISIBLE);
            switch (previousTime){
                case 0:
                    remindTimeTv.setText("发生时");
                    break;
                case 5:
                    remindTimeTv.setText("提前5分钟");
                    break;
                case 10:
                    remindTimeTv.setText("提前10分钟");
                    break;
                case 30:
                    remindTimeTv.setText("提前30分钟");
                    break;
                case 60:
                    remindTimeTv.setText("提前1小时");
                    break;
                case 1 * 24 * 60:
                    remindTimeTv.setText("提前一天");
                    break;
            }
        }


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
                                pt = 5;
                                break;
                            case R.id.plan_time2:
                                remindTimeTv.setText("提前10分钟");
                                pt = 10;
                                break;
                            case R.id.plan_time3:
                                remindTimeTv.setText("提前30分钟");
                                pt = 30;
                                break;
                            case R.id.plan_time4:
                                remindTimeTv.setText("提前1小时");
                                pt = 60;
                                break;
                            case R.id.plan_time5:
                                remindTimeTv.setText("提前一天");
                                pt = 1 * 24 * 60;
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
                        List<Plan> plans = LitePal.where("plantime = ?",planTime).find(Plan.class);

                        if(plan.getIsremind() == 1){
                            if(reminder.isChecked()){
                                if("请选择".equals(remindeTime)){
                                    Toast.makeText(getActivity(), "请设置有效提醒时间", Toast.LENGTH_SHORT).show();
                                }else if(!plans.isEmpty()){
                                    ToastUtil.ToastCenter(getContext(),'('+planTime+')'+"\n该时间点已有提醒事项咯");
                                }else {
                                    //修改系统日历事件
                                    if(pt != previousTime){
                                        CalendarReminderUtils.deleteCalendarEvent(getContext(),planTime);
                                        CalendarReminderUtils.addCalendarEvent(getContext(), content, planTime, planDate.getTime(), pt);
                                        savePlan();
                                        planModifyListener.planisModified();
                                        getFragmentManager().popBackStack();
                                    }
                                }
                            } else {
                                CalendarReminderUtils.deleteCalendarEvent(getContext(),planTime);
                                savePlan();
                                planModifyListener.planisModified();
                                getFragmentManager().popBackStack();
                            }
                        }else {
                            if(reminder.isChecked()){
                                if("请选择".equals(remindeTime)){
                                    Toast.makeText(getActivity(), "请设置有效提醒时间", Toast.LENGTH_SHORT).show();
                                }else if(!plans.isEmpty()) {
                                    Log.d("Plan_",plans.toString());
                                    ToastUtil.ToastCenter(getContext(),'('+planTime+')'+"该时间点已有提醒事项咯");
                                }else {
                                    //添加系统日历事件
                                    CalendarReminderUtils.addCalendarEvent(getContext(), content, planTime, planDate.getTime(), previousTime);
                                    savePlan();
                                    planModifyListener.planisModified();
                                    getFragmentManager().popBackStack();

                                }
                            }else {
                                savePlan();
                                planModifyListener.planisModified();
                                getFragmentManager().popBackStack();

                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }

            }
        });
    }

    private void savePlan(){
        //保存计划数据
        Plan plan1 = LitePal.find(Plan.class,plan.getId());
        plan1.setContent(contentEv.getText().toString());
        plan1.setPreviousTime(pt);
        plan1.setIsremind(reminder.isChecked()?1:0);
        plan1.setPlantime(plantimeTv.getText().toString());
        plan1.save();
        Log.d("Plan_","修改前："+plan.toString()+"\n修改后："+plan1.toString());
        ToastUtil.ToastCenter(getContext(),"已修改一项计划");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        planModifyListener = (PlanModifyListener) getActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        planModifyListener = null;
    }

    public interface PlanModifyListener{
        public void planisModified();
    }
}
